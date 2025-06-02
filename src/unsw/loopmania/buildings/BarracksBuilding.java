package unsw.loopmania.buildings;

import unsw.loopmania.observers.LocationObserver;

import org.javatuples.Pair;

import unsw.loopmania.combatants.Character;
import unsw.loopmania.managers.BuildingManager;

public class BarracksBuilding extends Building implements LocationObserver<Character> {

	public BarracksBuilding(Pair<Integer, Integer> position) {
		super(position);
		super.setEntityImageByPath("src/images/barracks.png");
	}

	@Override
	public void update(Character entity) {
		if ((entity.getX() == getX()) && (entity.getY() == getY())) {
			entity.addAlliedSoldier();
		}
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
		manager.addCharacterObserverBuilding(this);
	}

	@Override
	public void removeFromBuildingManager(BuildingManager manager) {
		manager.removeBuilding(this);
		manager.removeCharacterObserverBuilding(this);
	}
	
}
