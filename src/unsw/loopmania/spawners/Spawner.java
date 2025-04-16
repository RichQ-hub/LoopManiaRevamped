package unsw.loopmania.spawners;

import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.entity.Entity;

public interface Spawner {
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath);
}
