package unsw.loopmania.buildings;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.combatants.Zombie;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;
import unsw.loopmania.spawners.Spawner;

public class ZombiePitBuilding extends Building implements Spawner {
	private LoopManiaWorld world;
	private Pair<Integer, Integer> spawnLocation;

	public ZombiePitBuilding(Pair<Integer, Integer> position, Pair<Integer, Integer> spawnLocation, LoopManiaWorld world) {
		super(position);
		this.spawnLocation = spawnLocation;
		this.world = world;
		super.setEntityImageByPath("src/images/zombie_pit.png");
	}

	// Spawns a zombie on every cycle.
	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> zombies = new ArrayList<>();
		PathPosition pos = new PathPosition(orderedPath.indexOf(spawnLocation), orderedPath);
		Zombie newZombie = new Zombie(pos);
		zombies.add(newZombie);
		battleManager.addEnemy(newZombie);

		// Make all enemy observers in the building subscribe to this new zombie.
		BuildingManager bm = world.getBuildingManager();
		bm.subscribeNewEnemy(newZombie);
		return zombies;
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
		manager.addSpawner(this);
	}

	@Override
	public void removeFromBuildingManager(BuildingManager manager) {
		manager.removeBuilding(this);
		manager.removeSpawner(this);
	}
	
}
