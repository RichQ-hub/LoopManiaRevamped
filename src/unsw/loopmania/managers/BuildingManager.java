package unsw.loopmania.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.combatants.Enemy;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.observers.LocationObserver;
import unsw.loopmania.spawners.Spawner;
import unsw.loopmania.combatants.Character;

public class BuildingManager {
	private LoopManiaWorld world;
	private List<Building> buildings;
	private List<Spawner> spawnerBuildings;
	private List<Battleable> battleBuildings;

	// Observer Buildings (ones that update whenever the entities move).
	private List<LocationObserver<Enemy>> enemyObserverBuildings;;
	private List<LocationObserver<Character>> characterObserverBuildings;

	public BuildingManager(LoopManiaWorld world) {
		this.world = world;
		this.buildings = new ArrayList<>();
		this.spawnerBuildings = new ArrayList<>();
		this.battleBuildings = new ArrayList<>();
		this.enemyObserverBuildings = new ArrayList<>();
		this.characterObserverBuildings = new ArrayList<>();
	}

	public Building getBuildingByCoordinates(int x, int y) {
		for (Building b : buildings) {
			if (b.getX() == x && b.getY() == y) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Spawns map entities on every cycle.
	 * @param cycleCount
	 * @param orderedPath
	 * @return List of map entities to spawn.
	 */
	public List<Entity> spawnEntities(int cycleCount, List<Pair<Integer, Integer>> orderedPath) {
		List<Entity> entitiesToLoad = new ArrayList<>();
		for (Spawner s : spawnerBuildings) {
			entitiesToLoad.addAll(s.spawn(cycleCount, orderedPath, world.getBattleManager()));
		}
		return entitiesToLoad;
	}

	public void subscribeNewEnemy(Enemy enemy) {
		for (LocationObserver<Enemy> o : enemyObserverBuildings) {
			enemy.subscribe(o);
		}
	}

	public void removeInactiveBuildings() {
		List<Building> inactiveBuildings = buildings.stream().filter(b -> !b.isActive()).collect(Collectors.toList());
		for (Building b : inactiveBuildings) {
			removeBuildingFromManager(b);
			b.destroy();
		}
	}

	public void removeExpiredBuildings() {
		List<Building> expiredBuildings = buildings.stream().filter(b -> b.isExpired()).collect(Collectors.toList());
		for (Building b : expiredBuildings) {
			removeBuildingFromManager(b);
			b.destroy();
		}
	}

	public void decrementBuildingLifespans() {
		for (Building b : buildings) {
			b.decrementLifespan();
		}
	}

	// ==================================================================================
	// Battleable Buidlings.
	// ==================================================================================

	public List<Battleable> getBattleSupportBuildingsInRange(Character character) {
		List<Battleable> support = new ArrayList<>();
		for (Battleable b : battleBuildings) {
			if (b.isWithinBattleRadius(character)) {
				support.add(b);
			}
		}
		return support;
	}

	// ==================================================================================
	// Append methods.
	// ==================================================================================

	/**
	 * Double dispatch method
	 */
	public void addBuildingToManager(Building building) {
		building.addToBuildingManager(this);
	}

	public void addBuilding(Building building) {
		buildings.add(building);
	}

	public void addSpawner(Spawner spawner) {
		spawnerBuildings.add(spawner);
	}

	public void addBattleBuilding(Battleable building) {
		battleBuildings.add(building);
	}

	public void addEnemyObserverBuilding(LocationObserver<Enemy> observer) {
		enemyObserverBuildings.add(observer);

		// Add it to the enemy observer lists.
		BattleManager manager = world.getBattleManager();
		List<Enemy> enemies = manager.getEnemies();
		for (Enemy e : enemies) {
			e.subscribe(observer);
		}
	}

	public void addCharacterObserverBuilding(LocationObserver<Character> observer) {
		characterObserverBuildings.add(observer);

		Character character = world.getCharacter();
		character.subscribe(observer);
	}

	// ==================================================================================
	// Remove methods.
	// ==================================================================================

	/**
	 * Double dispatch method
	 */
	public void removeBuildingFromManager(Building building) {
		building.removeFromBuildingManager(this);
	}

	public void removeBuilding(Building building) {
		buildings.remove(building);
	}

	public void removeSpawner(Spawner spawner) {
		spawnerBuildings.remove(spawner);
	}

	public void removeBattleBuilding(Battleable building) {
		battleBuildings.remove(building);
	}

	public void removeEnemyObserverBuilding(LocationObserver<Enemy> observer) {
		enemyObserverBuildings.remove(observer);

		// Add it to the enemy observer lists.
		BattleManager manager = world.getBattleManager();
		List<Enemy> enemies = manager.getEnemies();
		for (Enemy e : enemies) {
			e.unsubscribe(observer);
		}
	}

	public void removeCharacterObserverBuilding(LocationObserver<Character> observer) {
		characterObserverBuildings.remove(observer);

		Character character = world.getCharacter();
		character.unsubscribe(observer);
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

}
