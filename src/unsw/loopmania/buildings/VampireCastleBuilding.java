package unsw.loopmania.buildings;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.BattleManager;
import unsw.loopmania.combatants.Vampire;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.spawners.Spawner;

/**
 * A basic form of building in the world.
 */
public class VampireCastleBuilding extends Building implements Spawner {

	private Pair<Integer, Integer> spawnLocation;

	public VampireCastleBuilding(Pair<Integer, Integer> position, Pair<Integer, Integer> spawnLocation) {
        super(position);
		this.spawnLocation = spawnLocation;
		super.setEntityImageByPath("src/images/vampire_castle_building_purple_background.png");
    }

	@Override
	public void addToBattleManager(BattleManager manager) {
		manager.addBuilding(this);
	}

	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath) {
		List<Entity> vampires = new ArrayList<>();
		PathPosition pos = new PathPosition(orderedPath.indexOf(spawnLocation), orderedPath);
		vampires.add(new Vampire(pos));
		return vampires;
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
		manager.addSpawner(this);
	}

	public Pair<Integer, Integer> getSpawnLocation() {
		return spawnLocation;
	}

	public void setSpawnLocation(Pair<Integer, Integer> spawnLocation) {
		this.spawnLocation = spawnLocation;
	}
}
