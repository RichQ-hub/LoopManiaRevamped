package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.entity.StaticEntity;
import unsw.loopmania.managers.BuildingManager;

// TODO: Add a duration functionality for buildings to last only a certain amount of cycles.
public abstract class Building extends StaticEntity {
	private boolean isActive;

	public Building(Pair<Integer, Integer> position) {
		super(position);
		this.isActive = true;
	}

	public abstract void addToBuildingManager(BuildingManager manager);
	public abstract void removeFromBuildingManager(BuildingManager manager);

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
