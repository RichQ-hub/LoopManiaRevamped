package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.managers.BuildingManager;

public class TowerBuilding extends Building {

	public TowerBuilding(Pair<Integer, Integer> position) {
		super(position);
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
	}
	
}
