package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.managers.BuildingManager;

public class HeroCastleBuilding extends Building {

	// -1 Represents a building is permanent.
	private static final int LIFESPAN = -1;

	public HeroCastleBuilding(Pair<Integer, Integer> position) {
		super(position, LIFESPAN);
		super.setEntityImageByPath("src/images/heros_castle.png");
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
	}

	@Override
	public void removeFromBuildingManager(BuildingManager manager) {
		manager.removeBuilding(this);
	}
	
}
