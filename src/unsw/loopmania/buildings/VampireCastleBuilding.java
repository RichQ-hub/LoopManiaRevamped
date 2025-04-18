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
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> vampires = new ArrayList<>();
		if (cycleCount % 2 == 0) {
			PathPosition pos = new PathPosition(orderedPath.indexOf(spawnLocation), orderedPath);
			Vampire newVampire = new Vampire(pos);
			vampires.add(newVampire);
			battleManager.addBattleableEntity(newVampire);
		}
		return vampires;
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		manager.addBuilding(this);
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
