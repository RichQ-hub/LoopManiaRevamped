package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.managers.BuildingManager;
import unsw.loopmania.observers.LocationObserver;

public class VillageBuilding extends Building implements LocationObserver<Character> {

	private static final int LIFESPAN = 3;

	public VillageBuilding(Pair<Integer, Integer> position) {
		super(position, LIFESPAN);
		super.setEntityImageByPath("src/images/village.png");
	}

	/**
	 * Heal the character by 8.
	 */
	@Override
	public void update(Character entity) {
		if ((entity.getX() == getX()) && (entity.getY() == getY())) {
			BattleAttributes attr = entity.getBattleAttributes();
			attr.setHealth(attr.getHealth() + 8);
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
