package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;
import org.javatuples.Pair;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.cards.BarracksCard;
import unsw.loopmania.cards.CampfireCard;
import unsw.loopmania.cards.Card;
import unsw.loopmania.cards.TrapCard;
import unsw.loopmania.cards.VillageCard;
import unsw.loopmania.cards.ZombiePitCard;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.inventory.InventoryManager;
import unsw.loopmania.items.Armour;
import unsw.loopmania.items.EquipmentItem;
import unsw.loopmania.items.Helmet;
import unsw.loopmania.items.Item;
import unsw.loopmania.items.Shield;
import unsw.loopmania.items.Staff;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Sword;
import unsw.loopmania.items.TheOneRing;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;
import unsw.loopmania.managers.CardManager;
import unsw.loopmania.combatants.Character;

import java.util.EnumMap;

import java.io.File;
import java.io.IOException;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEM,
	EQUIPMENT
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

	/**
	 * Labels
	 */

	@FXML
    private Label healthLabel;

	@FXML
    private Label goldLabel;

	@FXML
    private Label expLabel;

	@FXML
    private Label cycleLabel;

	@FXML
    private Label alliedSoldierLabel;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

	/**
	 * Backend Models. Use the following variables to control the backend model.
	 */
    private boolean isPaused;
    private LoopManiaWorld world;
	private InventoryManager inventoryManager;
	private BuildingManager buildingManager;
	private CardManager cardManager;
	private BattleManager battleManager;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the building/item which should be dropped
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * Object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;
	private MenuSwitcher victoryMenuSwitcher;
	private MenuSwitcher gameOverSwitcher;
	private MenuSwitcher shopSwitcher;

    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
		// Initialise world.
        this.world = world;

		// Grab entity managers.
		this.inventoryManager = world.getInventoryManager();
		this.buildingManager = world.getBuildingManager();
		this.cardManager = world.getCardManager();
		this.battleManager = world.getBattleManager();

		// Initialise entity images.
        this.entityImages = new ArrayList<>(initialEntities);
        this.currentlyDraggedImage = null;
        this.currentlyDraggedType = null;

        // initialize them all...
        this.gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        this.anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        this.anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        this.gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        this.gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

	/**
	 * Initialise function runs all the setup code AFTER the fxml view has loaded and this controller
	 * is attached to it. It is called when we run gameLoader.load() function in LoopManiaApplication.java.
	 */
    @FXML
    public void initialize() {
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);
		Image pathTilesImg = loadImage("src/images/32x32GrassAndDirtPath.png");
		Image inventorySlotImg = loadImage("src/images/empty_slot.png");

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getMapWidth(); x++) {
            for (int y = 0; y < world.getMapHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImg);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages) {
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x = 0; x < world.getMapWidth(); x++) {
            ImageView groundView = new ImageView(pathTilesImg);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImg);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

		// Initialise equipped items gridpane
		Image helmetSlotImg = loadImage("src/images/helmet_slot.png");
		Image chestpieceSlotImg = loadImage("src/images/armour_unequipped.png");
		Image shieldSlotImg = loadImage("src/images/shield_unequipped.png");
		Image weaponSlotImg = loadImage("src/images/sword_unequipped.png");
		ImageView helmetSlotView = new ImageView(helmetSlotImg);
		ImageView chestpieceSlotView = new ImageView(chestpieceSlotImg);
		ImageView shieldSlotView = new ImageView(shieldSlotImg);
		ImageView weaponSlotView = new ImageView(weaponSlotImg);
		equippedItems.add(helmetSlotView, 0, 0);
		equippedItems.add(chestpieceSlotView, 1, 0);
		equippedItems.add(shieldSlotView, 2, 0);
		equippedItems.add(weaponSlotView, 3, 0);

		// Bind Label Properties.
		Character character = world.getCharacter();
		healthLabel.textProperty().bind(character.getBattleAttributes().getHealthProperty().asString());
		goldLabel.textProperty().bind(character.getGoldProperty().asString());
		expLabel.textProperty().bind(character.getExpProperty().asString());
		cycleLabel.textProperty().bind(world.getCycleProperty().asString());
        alliedSoldierLabel.textProperty().bind(character.getSoldierCountProperty().asString());


        // Create the draggable icon. Initially the dragged entity is invisible, since we aren't dragging anything.
		// But once the user drags some entity, then the dragged entity gets set to the entity (i.e. sword item)
		// that started dragging.
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

		// TESTING: Initially give the character all items.
		Sword sword = new Sword();
		Shield shield = new Shield();
		Stake stake = new Stake();
		Armour armour = new Armour();
		Helmet helm = new Helmet();
		Staff staff = new Staff();
		TheOneRing ring = new TheOneRing();
		onLoadItem(inventoryManager.addItemToInventory(sword));
		onLoadItem(inventoryManager.addItemToInventory(shield));
		onLoadItem(inventoryManager.addItemToInventory(stake));
		onLoadItem(inventoryManager.addItemToInventory(armour));
		onLoadItem(inventoryManager.addItemToInventory(helm));
		onLoadItem(inventoryManager.addItemToInventory(staff));
		onLoadItem(inventoryManager.addItemToInventory(ring));

		VillageCard village = new VillageCard();
		BarracksCard barracksCard = new BarracksCard();
		TrapCard trap1 = new TrapCard();
		CampfireCard camp = new CampfireCard();
		ZombiePitCard zombiePit = new ZombiePitCard();
		onLoadCard(cardManager.addCard(barracksCard));
		onLoadCard(cardManager.addCard(village));
		onLoadCard(cardManager.addCard(trap1));
		onLoadCard(cardManager.addCard(camp));
		onLoadCard(cardManager.addCard(zombiePit));
    }

	public Image loadImage(String pathname) {
		return new Image((new File(pathname)).toURI().toString());
	}

    /**
     * create and run the timer
     */
    public void startTimer() {
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
			// Start of a new cycle if the character is at the castle.
			if (world.isCharacterAtCastle()) {
				// Check if the goal has been achieved.
				if (world.isGoalAchieved()) {
					pause();
					switchToVictoryMenu();
				}

				// Open shop menu.
				switchToShop();
				
				// Spawn new enemies when the character is at the start.
				List<Entity> newMapEntities = buildingManager.spawnEntities(world.getCycleCount(), world.getOrderedPath());
				for (Entity e : newMapEntities) {
					onLoadMapEntity(e);
				}

                // Decrement building lifespans.
                buildingManager.decrementBuildingLifespans();

				// Remove any expired buildings.
				buildingManager.removeExpiredBuildings();
			}
			
			// Move all moving entities.
            world.runTickMoves();

			// Pickup any path items by the character on every tick.
			List<Item> items = world.pickupPathItems();
			for (Item i : items) {
				onLoadItem(inventoryManager.addItemToInventory(i));
			}

			// Run battles on every tick.
            List<Battleable> defeatedEnemies = battleManager.battle();
			if (defeatedEnemies != null) {
				for (Battleable e: defeatedEnemies) {
					reactToEnemyDefeat(e);
				}
			}

			// Check if the character is dead.
			if (!world.getCharacter().isAlive()) {
				pause();
				switchToGameOver();
			}

            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause() {
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

	public void resume() {
		isPaused = false;
		System.out.println("resuming");
		timeline.play();
	}

    public void terminate() {
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(Battleable enemy) {
		Loot loot = enemy.dropLoot();

		for (Item i : loot.getItems()) {
			inventoryManager.addItemToInventory(i);
			onLoadItem(i);
		}

		for (Card c : loot.getCards()) {
			cardManager.addCard(c);
			onLoadCard(c);
		}

		Character character = world.getCharacter();
		character.setGold(character.getGold() + loot.getGold());
		character.setExp(character.getExp() + loot.getExp());
    }

	/**
	 * Loads an item onto the unequipped items gridpane. Can be dragged from the unequipped
	 * inventory gridpane to the equipped items gridpane.
	 * 
	 * IMPORTANT: This assumes that the item has already been set with the correct coords
	 * in the backend model inventory.
	 * @param item
	 */
	public void onLoadItem(Item item) {
		if (item == null) {
			return;
		}
		ImageView view = new ImageView(item.getEntityImage());
		addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);
		addEntity(item, view);
		unequippedInventory.getChildren().add(view);

		// TEST
		view.setOnMouseClicked((e) -> {
			Character character = world.getCharacter();
			InventoryManager manager = world.getInventoryManager();
            if (e.getButton() == MouseButton.SECONDARY) {
				item.useItem(character, manager);
			}
        });
	}

	/**
	 * Loads an equipment item onto the equipped items gridpane. Can be dragged from the equipped
	 * items gridpane back to the unequipped inventory gridpane.
	 * @param equipment
	 */
	private void onLoadEquipmentItem(EquipmentItem equipment) {
		if (equipment == null) {
			return;
		}
		ImageView view = new ImageView(equipment.getEntityImage());
		addDragEventHandlers(view, DRAGGABLE_TYPE.EQUIPMENT, equippedItems, unequippedInventory);
		addEntity(equipment, view);
		equippedItems.getChildren().add(view);
	}

	/**
	 * Loads cards into the card gridpane.
	 * @param card
	 */
	private void onLoadCard(Card card) {
		if (card == null) {
			return;
		}
		ImageView view = new ImageView(card.getEntityImage());
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);
        addEntity(card, view);
        cards.getChildren().add(view);
	}

	/**
	 * Loads map entites including buildings and enemies onto the game map gridpane.
	 * @param entity
	 */
	private void onLoadMapEntity(Entity entity) {
		if (entity == null) {
			return;
		}
		ImageView view = new ImageView(entity.getEntityImage());
        addEntity(entity, view);
        squares.getChildren().add(view);
	}

    /**
     * Add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane) {
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

		/**
		 * MY_NOTE: Gridpane encompasses the entire scene of the game. So here we are attatching an event handler
		 * that is triggered whenever we drop something (i.e. a card or item) onto the grid pane (which can
		 * include any tile in the game like the unequipped inventory or the actual game tiles where the character
		 * and enemies are).
		 * 
		 * IMPORTANT: 
		 * 		targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
		 * 
		 * The below gridPaneSetOnDragDropped simply defines the event handler and puts it in the enum map with the
		 * key of {draggableType}. It does not actually attach the event handler to the grid pane. Instead, the above
		 * line of code defined in the method addDragEventHandlers() is where we attach it to the targetGridPane
		 * node. So whenever the targetGridPane (the pane where we want to drop something like an item onto the equipped
		 * inventory) detects the user wants to drop something they're dragging on it, then we trigger the below event.
		 */
        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // TODO = for being more selective about where something can be dropped, consider applying additional if-statement logic
                /*
                 * You might want to design the application so dropping at an invalid location drops at the most
				 * recent valid location hovered over, or simply allow the card/item to return to its slot (the
				 * latter is easier, as you won't have to store the last valid drop location!).
                 */
                if (currentlyDraggedType == draggableType) {
                    // Problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();

					// This node is UI element that the current drag event is passing over or interacting with.
                    Node node = event.getPickResult().getIntersectedNode();

					// If the user is currently dragging something (indicated by the dragboard having an image),
					// AND if the node being hovered on is NOT the target gridpane, but the actual cells within it.
                    if (node != targetGridPane && db.hasImage()) {
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);

						// These are coordinates of the drop location.
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;

						// Grab the image that we are currently dragging.
                        // ImageView image = new ImageView(db.getImage());

						// We get the x and y coords of the currently dragged image from where it was originally
						// dragged from (the starting x and y before the image was dragged).
                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType) {
                            case CARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);

                                Building newBuilding = world.convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
								if (newBuilding == null) {
									currentlyDraggedImage.setVisible(true);
									break; 
								}

                                onLoadMapEntity(newBuilding);

                                break;
                            case ITEM:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
								if (targetGridPane != equippedItems) {
									break;
								}

								Pair<EquipmentItem, Item> items = inventoryManager.equipInventoryItemByCoordinates(nodeX, nodeY, x, y);
								
								// Ensure item was successfully equipped.
								if (items == null) {
									// IMPORTANT: Before we started dragging the item, we set it's ImageView to invisible.
									// Technically, the ImageView is still on the unequipped inventory because
									// we just set it invisble for the drag effect. Since equipping this item
									// was unsuccessful, we set it back to visible which reveals its image back on
									// the unequipped inventory.
									currentlyDraggedImage.setVisible(true);
									break;
								}

								EquipmentItem equippedItem = items.getValue0();
								Item oldItem = items.getValue1();

								if (equippedItem != null) {
									unequippedInventory.getChildren().remove(currentlyDraggedImage);
									onLoadEquipmentItem(equippedItem);
								}

								if (oldItem != null) {
									// Remove the intersected node (which is the ImageView of old item).
									equippedItems.getChildren().remove(node);
									onLoadItem(oldItem);
								}

                                break;
							case EQUIPMENT:
								// For items in the equipped inventory GridPane. Here we add a drag handler for
								// items dragged FROM the equipped inventory to the unequipped inventory.
								removeDraggableDragEventHandlers(draggableType, targetGridPane);

								if (targetGridPane != unequippedInventory) {
									break;
								}
								
								Item unequippedItem = inventoryManager.unequipEquipmentItemByCoordinates(nodeX, nodeY);
								if (unequippedItem == null) {
									// If we couldn't unequip the item because the inventory was full, we break.
									currentlyDraggedImage.setVisible(true);
									break; 
								}

								// Remove the currently dragged image, which is the equipment item we
								// want to remove from the equipment gridpane.
								equippedItems.getChildren().remove(currentlyDraggedImage);

								// Load the newly unequipped item back into the unequipped gridpane.
								onLoadItem(unequippedItem);
					
								break;
                            default:
                                break;
                        }
                        
						node.setOpacity(1);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

		/**
		 * Handles event for when we drag over the background (entire game scene).
		 */
        // This doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
		// which stops propagating the event upwards.
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>() {
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    if (event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null) {
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

		/**
		 * Handles event for when we drag drop on the background (entire game scene).
		 */
        // This doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event.
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != anchorPaneRoot && db.hasImage()) {
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane) {
		/**
		 * As soon as a node is being dragged by the user, the below event handler gets triggered.
		 */
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
				// Set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedImage = view;
                currentlyDraggedType = draggableType;
                // Drag was detected, start drap-and-drop gesture
                // Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

				// Make sure the dragged entity image moves in tandem with the user cursor.
                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                draggedEntity.setImage(view.getImage());
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // To be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()) {
                    // Events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the
						// location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
								// The drag-and-drop gesture entered the target
								// show the user that it is an actual gesture target
                                if (event.getGestureSource() != n && event.getDragboard().hasImage()) {
									Integer cIndex = GridPane.getColumnIndex(n);
									Integer rIndex = GridPane.getRowIndex(n);
									int x = cIndex == null ? 0 : cIndex;
									int y = rIndex == null ? 0 : rIndex;

									// We get the x and y coords of the currently dragged image from where it was originally
									// dragged from (the starting x and y before the image was dragged).
									Node source = (Node) event.getGestureSource();
									int sourceX = GridPane.getColumnIndex(source);
									int sourceY = GridPane.getRowIndex(source);
									switch (draggableType) {
										case CARD:
											Card card = cardManager.getCardByCoordinates(sourceX, sourceY);
											if (card != null && card.isValidDropLocation(world, x, y)) {
												n.setOpacity(0.7);
											}
											break;
										case ITEM:
											if (inventoryManager.canEquipInventoryItemByCoordinates(sourceX, sourceY, x, y)) {
												n.setOpacity(0.7);
											}
											break;
										default:
											break;
									}
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, you could program the
						// game so if the new highlight location is invalid the highlighting doesn't change, or leave this as-is
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * Remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane) {
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        // TODO = handle additional key presses, e.g. for consuming a health potion.
        switch (event.getCode()) {
        case SPACE:
            if (isPaused) {
                startTimer();
            }
            else {
                pause();
            }
            break;
        default:
            break;
        }
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
	 * 
	 * You can think of the entity like a sword item and the node as its
	 * corresponding ImageView.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we
	 * need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane.
	 * So it is vital this is handled in this Controller class.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

		// X-coord listener method.
        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };

		// Y-coord listener method.
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // If need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part

		// This below code makes it so that if the x coord of the entity changes, we
		// do something with the node, i.e. the node listens to entity changes.
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
			.onAttach((o, l) -> o.addListener(xListener))
			.onDetach((o, l) -> {
				// Cleanup code for when the enetity is deleted (seen below),
				// so we delete the image, remove the listener, etc.
				o.removeListener(xListener);
				entityImages.remove(node);
				squares.getChildren().remove(node);
				cards.getChildren().remove(node);
				equippedItems.getChildren().remove(node);
				unequippedInventory.getChildren().remove(node);
			})
            .buildAttached();

        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
			.onAttach((o, l) -> o.addListener(yListener))
			.onDetach((o, l) -> {
				o.removeListener(yListener);
				entityImages.remove(node);
				squares.getChildren().remove(node);
				cards.getChildren().remove(node);
				equippedItems.getChildren().remove(node);
				unequippedInventory.getChildren().remove(node);
			})
			.buildAttached();

		// Run the onAttach() methods as seen above for both x and y coords of the entities,
		// which essentially adds the listener method to berun whenever the x or y
		// coords change.
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
				// This function runs when the BooleanProperty variable for the entity called
				// shouldExist() is changed to false, essentially meaning the entity is destroyed. 
				// Hence we run cleanup functions as seen above in the onDetach() methods.
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel) {
        System.out.println("\n###########################################");
        System.out.println("current method = " + currentMethodLabel);
        System.out.println("In application thread? = " + Platform.isFxApplicationThread());
        System.out.println("Current system time = " + java.time.LocalDateTime.now().toString().replace('T', ' '));
    }

	// ==================================================================================
	// Menu Switchers.
	// ==================================================================================

	public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        pause();
        mainMenuSwitcher.switchMenu();
    }

	public void setVictoryMenuSwitcher(MenuSwitcher victoryMenuSwitcher) {
        this.victoryMenuSwitcher = victoryMenuSwitcher;
    }

    private void switchToVictoryMenu() {
        victoryMenuSwitcher.switchMenu();
    }

	public void setGameOverSwitcher(MenuSwitcher gameOverSwitcher) {
        this.gameOverSwitcher = gameOverSwitcher;
    }

    private void switchToGameOver() {
        gameOverSwitcher.switchMenu();
    }

	public void setShopSwitcher(MenuSwitcher shopSwitcher) {
        this.shopSwitcher = shopSwitcher;
    }

    private void switchToShop() {
		pause();
        shopSwitcher.switchMenu();
    }

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public LoopManiaWorld getWorld() {
		return world;
	}

}
