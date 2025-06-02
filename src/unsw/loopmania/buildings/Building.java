package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.entity.StaticEntity;
import unsw.loopmania.managers.BuildingManager;

public abstract class Building extends StaticEntity {
	private boolean isActive;
	private int cycleLifespan; // -1 Represents a building is permanent.

	public Building(Pair<Integer, Integer> position, int cycleLifespan) {
		super(position);
		this.isActive = true;
		this.cycleLifespan = cycleLifespan;
	}

	public abstract void addToBuildingManager(BuildingManager manager);
	public abstract void removeFromBuildingManager(BuildingManager manager);

	public void decrementLifespan() {
		if (cycleLifespan > 0) {
			cycleLifespan--;
		}
	}

	public boolean isExpired() {
		return cycleLifespan == 0;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
