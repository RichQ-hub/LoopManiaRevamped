package unsw.loopmania.spawners;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.combatants.Doggie;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;

public class DoggieSpawner implements Spawner {
	private LoopManiaWorld world;

	public DoggieSpawner(LoopManiaWorld world) {
		this.world = world;
	}

	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> spawnEntities = new ArrayList<>();
		if (cycleCount == 3) {
			Pair<Integer, Integer> spawnPos = world.getPositionToSpawnEnemy();
			int spawnIdx = orderedPath.indexOf(spawnPos);
			PathPosition pathPos = new PathPosition(spawnIdx, orderedPath);

			Doggie doggie = new Doggie(pathPos);
			spawnEntities.add(doggie);
			battleManager.addEnemy(doggie);

			// Subscribe enemy observers to overse this new enemy.
			BuildingManager bm = world.getBuildingManager();
			bm.subscribeNewEnemy(doggie);
		}
		return spawnEntities;
	}
	
}
