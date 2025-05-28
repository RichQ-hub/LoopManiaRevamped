package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.managers.BuildingManager;

public class HeroCastleBuilding extends Building {

	public HeroCastleBuilding(Pair<Integer, Integer> position) {
		super(position);
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
