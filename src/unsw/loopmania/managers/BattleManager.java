package unsw.loopmania.managers;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.combatants.Combatant;
import unsw.loopmania.combatants.Enemy;
import unsw.loopmania.entity.Battleable;

public class BattleManager {
	private LoopManiaWorld world;
	private Character character;
	private List<Enemy> enemies;
	private List<Building> buildings;

	public BattleManager(LoopManiaWorld world, Character character) {
		this.world = world;
		this.character = character;
		this.enemies = new ArrayList<>();
		this.buildings = new ArrayList<>();
	}

	/**
     * Run the expected battles in the world, based on current world state.
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        for (Enemy e : enemies) {
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) < 4) {
                // fight... (Instakill for now)
                defeatedEnemies.add(e);
            }
        }
        for (Enemy e: defeatedEnemies) {
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            killEnemy(e);
        }
        return defeatedEnemies;
    }

	/**
     * Kill an enemy.
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy) {
        enemy.destroy();
        enemies.remove(enemy);
    }

	public void moveEnemies() {
		for (Combatant e : enemies) {
			e.move();
		}
	}

	/**
	 * Double dispatch method.
	 * @param entity
	 */
	public void addBattleableEntity(Battleable entity) {
		entity.addToBattleManager(this);
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	public void addBuilding(Building building) {
		buildings.add(building);
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public LoopManiaWorld getWorld() {
		return world;
	}

	public void setWorld(LoopManiaWorld world) {
		this.world = world;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}
}
