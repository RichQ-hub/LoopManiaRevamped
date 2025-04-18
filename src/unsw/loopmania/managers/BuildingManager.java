package unsw.loopmania.managers;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.spawners.Spawner;

public class BuildingManager {
	private LoopManiaWorld world;
	private List<Building> buildings;
	private List<Spawner> spawners;

	public BuildingManager(LoopManiaWorld world) {
		this.world = world;
		this.buildings = new ArrayList<>();
		this.spawners = new ArrayList<>();
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
	 * Double dispatch method
	 */
	public void addBuildingSpawner(Building building) {
		building.addToBuildingManager(this);
	}

	public void addBuilding(Building building) {
		buildings.add(building);
	}

	public void addSpawner(Spawner spawner) {
		spawners.add(spawner);
	}

	public List<Entity> spawnEntities(int cycleCount, List<Pair<Integer, Integer>> orderedPath) {
		List<Entity> entitiesToLoad = new ArrayList<>();
		for (Spawner s : spawners) {
			entitiesToLoad.addAll(s.spawn(cycleCount, orderedPath, world.getBattleManager()));
		}
		return entitiesToLoad;
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

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public void setSpawners(List<Spawner> spawners) {
		this.spawners = spawners;
	}

}
