package unsw.loopmania.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.managers.BattleManager;

public class HealthPotionSpawner implements Spawner {
	private LoopManiaWorld world;

	public HealthPotionSpawner(LoopManiaWorld world) {
		this.world = world;
	}

	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> potions = new ArrayList<>();

		Random rand = new Random();

		// Spawn between 0 - 2 potions per cycle.
		int numPotions = rand.nextInt(0, 3);

		for (int i = 0; i < numPotions; i++) {
			Pair<Integer, Integer> newPos = world.getPositionToSpawnEnemy();

			HealthPotion newPotion = new HealthPotion();
			newPotion.x().set(newPos.getValue0());
			newPotion.y().set(newPos.getValue1());

			potions.add(newPotion);
			world.addPathItem(newPotion);
		}

		return potions;
	}
	
}
