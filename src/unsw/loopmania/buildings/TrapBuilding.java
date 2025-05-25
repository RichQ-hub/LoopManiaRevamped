package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.managers.BuildingManager;

public class TrapBuilding extends Building {

	public TrapBuilding(Pair<Integer, Integer> position) {
		super(position);
		super.setEntityImageByPath("src/images/trap.png");
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
	}
	
}
