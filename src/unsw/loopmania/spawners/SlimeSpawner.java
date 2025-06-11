package unsw.loopmania.spawners;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.combatants.Slime;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;

public class SlimeSpawner implements Spawner {

	private LoopManiaWorld world;

	public SlimeSpawner(LoopManiaWorld world) {
		this.world = world;
	}

	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> slimes = new ArrayList<>();

		PathPosition newPos = new PathPosition(orderedPath.indexOf(world.getPositionToSpawnEnemy()), orderedPath);
		Slime newSlime = new Slime(newPos);
		slimes.add(newSlime);
		battleManager.addEnemy(newSlime);

		// Subscribe all the enemy observers into this slug list.
		BuildingManager bm = world.getBuildingManager();
		bm.subscribeNewEnemy(newSlime);

		return slimes;
	}
	
}
