package unsw.loopmania.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.combatants.Slug;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.managers.BattleManager;

public class SlugSpawner implements Spawner {

	private LoopManiaWorld world;

	public SlugSpawner(LoopManiaWorld world) {
		this.world = world;
	}

	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> slugs = new ArrayList<>();

		// Spawns between 1-3 slugs per cycle.
		int numSlugs = new Random().nextInt(1, 4);

		for (int i = 0; i < numSlugs; i++) {
			PathPosition newPos = new PathPosition(orderedPath.indexOf(world.getPositionToSpawnEnemy()), orderedPath);
			Slug newSlug = new Slug(newPos);
			slugs.add(newSlug);
			battleManager.addBattleableEntity(newSlug);
		}

		return slugs;
	}
	
}
