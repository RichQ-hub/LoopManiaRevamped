package unsw.loopmania.spawners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.entity.GoldPile;
import unsw.loopmania.managers.BattleManager;

public class GoldSpawner implements Spawner {
	private LoopManiaWorld world;

	public GoldSpawner(LoopManiaWorld world) {
		this.world = world;
	}

	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> goldPiles = new ArrayList<>();

		Random rand = new Random();

		// Spawns between 2 - 3 piles per cycle.
		int numPiles = rand.nextInt(2, 4);

		for (int i = 0; i < numPiles; i++) {
			Pair<Integer, Integer> newPos = world.getPositionToSpawnEnemy();
			int amount = rand.nextInt(10, 16);

			GoldPile newPile = new GoldPile(newPos, amount);
			goldPiles.add(newPile);
			world.addPathEntity(newPile);

			// Subscribe the new gold to the character.
			Character character = world.getCharacter();
			character.subscribe(newPile);

		}

		return goldPiles;
	}
	
}
