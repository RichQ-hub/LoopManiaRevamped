package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.entity.StaticEntity;
import unsw.loopmania.managers.BuildingManager;

public abstract class Building extends StaticEntity {

	public Building(Pair<Integer, Integer> position) {
		super(position);
	}

	public abstract void addToBuildingManager(BuildingManager manager);
	
}
