package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.buildings.HeroCastleBuilding;
import unsw.loopmania.cards.Card;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.goals.Goal;
import unsw.loopmania.inventory.InventoryManager;
import unsw.loopmania.items.Item;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;
import unsw.loopmania.managers.CardManager;
import unsw.loopmania.spawners.DoggieSpawner;
import unsw.loopmania.spawners.ElanMuskeSpawner;
import unsw.loopmania.spawners.GoldSpawner;
import unsw.loopmania.spawners.HealthPotionSpawner;
import unsw.loopmania.spawners.SlimeSpawner;
import unsw.loopmania.spawners.SlugSpawner;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    /**
     * Width of the world in GridPane cells
     */
    private int mapWidth;

    /**
     * Height of the world in GridPane cells
     */
    private int mapHeight;

    /**
     * Generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;
	private HeroCastleBuilding heroCastle;
	private IntegerProperty cycleCount;

	/**
	 * Battle System.
	 */
	private BattleManager battleManager;

	/**
	 * Entity Managers.
	 */
	private InventoryManager inventoryManager;
	private BuildingManager buildingManager;
	private CardManager cardManager;

    /**
     * List of {x, y} coordinate pairs in the order by which moving entities traverse them.
     */
    private List<Pair<Integer, Integer>> orderedPath;

	/**
	 * Goal for the map.
	 */
	private Goal goal;

	// Items spawns in the map path for the character to pick up.
	private List<Entity> pathEntities;

	private List<Item> pathItems;

	/**
     * Create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int mapWidth, int mapHeight, List<Pair<Integer, Integer>> orderedPath, Goal goal) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.nonSpecifiedEntities = new ArrayList<>();
        this.character = null;
		this.heroCastle = null;
        this.orderedPath = orderedPath;
		this.cycleCount = new SimpleIntegerProperty(1);
		this.goal = goal;
		this.pathEntities = new ArrayList<>();
		this.pathItems = new ArrayList<>();

		// Entity Managers.
		this.inventoryManager = new InventoryManager();
		this.buildingManager = new BuildingManager(this);
		this.battleManager = new BattleManager(this, this.character);
		this.cardManager = new CardManager(this);

		// Add initial spawners.
		buildingManager.addSpawner(new GoldSpawner(this));
		buildingManager.addSpawner(new HealthPotionSpawner(this));
		buildingManager.addSpawner(new SlugSpawner(this));
		buildingManager.addSpawner(new ElanMuskeSpawner(this));
		buildingManager.addSpawner(new DoggieSpawner(this));
		buildingManager.addSpawner(new SlimeSpawner(this));
    }

	public boolean isGoalAchieved() {
		return goal.achievedGoal(this);
	}

    /**
     * Add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    /**
     * Run moves which occur with every tick without needing to spawn anything immediately.
     */
    public void runTickMoves() {
        character.move();

		battleManager.moveEnemies();

		buildingManager.removeInactiveBuildings();

		clearInactivePathEntities();
		character.clearDestroyedObservers();
		
		if (isCharacterAtCastle()) {
			setCycleCount(getCycleCount() + 1);
		}
    }

	/**
	 * On every tick, we check if the character is on a dropped item, in which case we pick it up.
	 * @return
	 */
	public List<Item> pickupPathItems() {
		List<Item> items = new ArrayList<>();
		for (Item i : pathItems) {
			if ((character.getX() == i.getX()) && (character.getY() == i.getY())) {
				// Create a copy beacuse we want to destroy the ImageView of the item on the map,
				// which we can then have the copy item ImageView load in the inventory.
				Item copy = i.copyItem();
				items.add(copy);
				i.destroy();
			}
		}

		clearInactivePathItems();

		return items;
	}

	/**
	 * Checks if the character is at the hero's castle (indicating the start of a new cycle).
	 * @return
	 */
	public boolean isCharacterAtCastle() {
		return (
			heroCastle.getX() == character.getX() &&
			heroCastle.getY() == character.getY()
		);
	}

	// ==================================================================================
	// Card and Building Methods.
	// ==================================================================================

	/**
     * Remove the card from the world, and spawn and return a building instead where the card was dropped.
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    public Building convertCardToBuildingByCoordinates(int cardX, int cardY, int buildingX, int buildingY) {
        // Get the card.
        Card card = cardManager.getCardByCoordinates(cardX, cardY);
		if (card == null) {
			return null;
		}

		// If the building location is not valid for the current card, return null.
		if (!card.isValidDropLocation(this, buildingX, buildingY)) {
			return null;
		}
        
        // Now spawn building
        Building newBuilding = card.createBuilding(this, buildingX, buildingY);
        buildingManager.addBuildingToManager(newBuilding);

        // Destroy the card
		cardManager.destroyCardByIndex(cardX);

        return newBuilding;
    }

	// ==================================================================================
	// Path Methods.
	// ==================================================================================

	public Pair<Integer, Integer> getAdjacentPath(int x, int y) {
		int[] rowNum = {-1, 0, 1, 0};
		int[] colNum = {0, 1, 0, -1};

		for (int i = 0; i < 4; i++) {
			int adjCellRow = x + rowNum[i];
			int adjCellCol = y + colNum[i];

			if (
				isValidCell(adjCellRow, adjCellCol) &&
				isOnPath(adjCellRow, adjCellCol)
			) {
				return new Pair<Integer, Integer>(adjCellRow, adjCellCol);
			}	
		}

		return null;
	}

	/**
	 * NOTE: Could possibly move this to the PathPosition class.
     * Get a randomly generated position that can be used to spawn an enemy.
	 * TODO: Could rename function name to getRandomPathPositionSpawn().
     * @return a random coordinate pair
     */
    public Pair<Integer, Integer> getPositionToSpawnEnemy() {
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));

		// Inclusive start and exclusive end of range of positions not allowed

		// 2 tiles behind the character on the path.
        int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
		// 3 tiles ahead of the character in the path.
        int endNotAllowed = (indexPosition + 3) % orderedPath.size();

		// Add all spawn candidates, with wraparound behaviour as we start with the end.
        for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()){
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // Do not spawn on hero's castle
        orderedPathSpawnCandidates.remove(orderedPath.get(0));

        // Choose a random position.
        return orderedPathSpawnCandidates.get(new Random().nextInt(orderedPathSpawnCandidates.size()));
    }
	
	public boolean isOnPath(int x, int y) {
		return orderedPath.contains(Pair.with(x, y));
	}

	public boolean isValidCell(int x, int y) {
		return (
			x >= 0 && x < mapWidth && 
           	y >= 0 && y < mapHeight
		);
	}

	public boolean isAdjacentToPath(int x, int y) {
		return getAdjacentPath(x, y) != null;
	}

	public boolean hasExistingBuilding(int x, int y) {
		return buildingManager.getBuildingByCoordinates(x, y) != null;
	}

	/**
     * Get a randomly generated position which could be used to spawn an enemy.
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    // private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition() {
    //     // TODO = modify this
        
    //     // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
    //     Random rand = new Random();
    //     int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
    //     // TODO = change based on spec
    //     if ((choice == 0) && (enemies.size() < 2)) {
    //         List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
    //         int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
    //         // inclusive start and exclusive end of range of positions not allowed

	// 		// 2 tiles behind the character on the path.
    //         int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();

	// 		// 3 tiles ahead of the character in the path.
    //         int endNotAllowed = (indexPosition + 3) % orderedPath.size();
    //         // note terminating condition has to be != rather than < since wrap around...
    //         for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()) {
    //             orderedPathSpawnCandidates.add(orderedPath.get(i));
    //         }

    //         // choose random choice
    //         Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

    //         return spawnPosition;
    //     }
    //     return null;
    // }

	// ==================================================================================
	// Path Entities Methods.
	// ==================================================================================

	public void addPathEntity(Entity entity) {
		pathEntities.add(entity);
	}

	public void clearInactivePathEntities() {
		this.pathEntities = pathEntities.stream().filter(e -> e.shouldExist().get()).collect(Collectors.toList());
	}

	// ==================================================================================
	// Path Items Methods.
	// ==================================================================================

	public void addPathItem(Item item) {
		pathItems.add(item);
	}

	public void clearInactivePathItems() {
		this.pathItems = pathItems.stream().filter(item -> item.shouldExist().get()).collect(Collectors.toList());
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	public BuildingManager getBuildingManager() {
		return buildingManager;
	}

	public void setBuildingManager(BuildingManager buildingManager) {
		this.buildingManager = buildingManager;
	}

	public CardManager getCardManager() {
		return cardManager;
	}

	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}

	public BattleManager getBattleManager() {
		return battleManager;
	}

	public void setBattleManager(BattleManager battleManager) {
		this.battleManager = battleManager;
	}

	public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

	public List<Pair<Integer, Integer>> getOrderedPath() {
		return orderedPath;
	}

	public void setOrderedPath(List<Pair<Integer, Integer>> orderedPath) {
		this.orderedPath = orderedPath;
	}
	
	public Character getCharacter() {
        return character;
    }

    /**
     * Set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
		this.battleManager.setCharacter(character);
		this.character.setInventory(inventoryManager);

    }

	public HeroCastleBuilding getHeroCastle() {
		return heroCastle;
	}

	/**
     * Set the castle. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
	public void setHeroCastle(HeroCastleBuilding heroCastle) {
		this.heroCastle = heroCastle;
	}

	public IntegerProperty getCycleProperty() {
        return cycleCount;
    }

	public int getCycleCount() {
        return cycleCount.get();
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount.set(cycleCount);
    }

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public List<Entity> getPathEntities() {
		return pathEntities;
	}

	public void setPathEntities(List<Entity> pathEntities) {
		this.pathEntities = pathEntities;
	}
}
