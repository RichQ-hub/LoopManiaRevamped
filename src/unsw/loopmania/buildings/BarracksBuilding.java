package unsw.loopmania.buildings;

import unsw.loopmania.observers.LocationObserver;

import org.javatuples.Pair;

import unsw.loopmania.combatants.Character;
import unsw.loopmania.managers.BuildingManager;

public class BarracksBuilding extends Building implements LocationObserver<Character> {

	public BarracksBuilding(Pair<Integer, Integer> position) {
		super(position);
		//TODO Auto-generated constructor stub
	}

	@Override
	public void update(Character entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addToBuildingManager'");
	}

	@Override
	public void removeFromBuildingManager(BuildingManager manager) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeFromBuildingManager'");
	}
	
}
