package unsw.loopmania.spawners;

import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.entity.Entity;
import unsw.loopmania.managers.BattleManager;

public interface Spawner {
	/**
	 * Spawns a list of a single entity type on the game path after certain world conditions are met
	 * (e.g. cycle count, etc).
	 * @param cycleCount
	 * @param orderedPath
	 * @param battleManager
	 * @return
	 */
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager);
}
