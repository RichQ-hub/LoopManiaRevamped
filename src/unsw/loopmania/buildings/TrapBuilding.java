package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.combatants.Enemy;
import unsw.loopmania.managers.BuildingManager;
import unsw.loopmania.observers.LocationObserver;

public class TrapBuilding extends Building implements LocationObserver<Enemy> {

	private static final int LIFESPAN = -1;

	public TrapBuilding(Pair<Integer, Integer> position) {
		super(position, LIFESPAN);
		super.setEntityImageByPath("src/images/trap.png");
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
		manager.addEnemyObserverBuilding(this);
	}

	@Override
	public void removeFromBuildingManager(BuildingManager manager) {
		manager.removeBuilding(this);
		manager.removeEnemyObserverBuilding(this);
	}

	/**
	 * Deal 20 dmg to enemies when they step over them.
	 */
	@Override
	public void update(Enemy entity) {
		if (entity.getX() == getX() && entity.getY() == getY()) {
            BattleAttributes attr = entity.getBattleAttributes();

			attr.reduceHealth(20);

			System.out.println(String.format("%s stepped on a trap, its health now is %f", entity.getClass().getSimpleName(), attr.getHealth()));

			// Destroy this trap.
			super.setActive(false); // Mark this trap to be removed from the building manager.
        }
	}

	@Override
	public boolean shouldObserverExist() {
		return shouldExist().get();
	}
}
