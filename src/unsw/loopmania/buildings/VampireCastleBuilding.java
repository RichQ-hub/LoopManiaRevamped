package unsw.loopmania.buildings;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.combatants.Vampire;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;
import unsw.loopmania.spawners.Spawner;

/**
 * A basic form of building in the world.
 */
public class VampireCastleBuilding extends Building implements Spawner {

	private LoopManiaWorld world;
	private Pair<Integer, Integer> spawnLocation;

	public VampireCastleBuilding(Pair<Integer, Integer> position, Pair<Integer, Integer> spawnLocation, LoopManiaWorld world) {
        super(position);
		this.spawnLocation = spawnLocation;
		this.world = world;
		super.setEntityImageByPath("src/images/vampire_castle_building_purple_background.png");
    }

	@Override
	public List<Entity> spawn(int cycleCount, List<Pair<Integer, Integer>> orderedPath, BattleManager battleManager) {
		List<Entity> vampires = new ArrayList<>();
		if (cycleCount % 2 == 0) {
			PathPosition pos = new PathPosition(orderedPath.indexOf(spawnLocation), orderedPath);
			Vampire newVampire = new Vampire(pos);
			vampires.add(newVampire);
			battleManager.addEnemy(newVampire);

			// Subscribe all the enemy observers into this slug list.
			BuildingManager bm = world.getBuildingManager();
			bm.subscribeNewEnemy(newVampire);
		}
		return vampires;
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

	public Pair<Integer, Integer> getSpawnLocation() {
		return spawnLocation;
	}

	public void setSpawnLocation(Pair<Integer, Integer> spawnLocation) {
		this.spawnLocation = spawnLocation;
	}

}
