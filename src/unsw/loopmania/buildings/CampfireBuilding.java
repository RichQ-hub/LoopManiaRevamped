package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.managers.BuildingManager;
import unsw.loopmania.observers.LocationObserver;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.effects.modifiers.DamageMultiplier;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.entity.Entity;

public class CampfireBuilding extends Building implements LocationObserver<Character> {

	private final double radius = 16;
	private EffectModifier buff;

	public CampfireBuilding(Pair<Integer, Integer> position) {
		super(position);
		super.setEntityImageByPath("src/images/campfire.png");
		this.buff = new DamageMultiplier(2);
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

	@Override
	public void update(Character entity) {
		BattleAttributes attr = entity.getBattleAttributes();
		if (withinRange(entity)) {
			attr.addAttackModifier(buff);
			// System.out.println(String.format("%s stepped on a trap, its health now is %f", entity.getClass().getSimpleName(), attr.getHealth()));
        } else {
			attr.removeAttackModifier(buff);
		}
	}

	public boolean withinRange(Entity entity) {
		return Math.pow((getX() - entity.getX()), 2) + Math.pow((getY() - entity.getY()), 2) <= radius;
	}

	public double getRadius() {
		return radius;
	}
	
}
