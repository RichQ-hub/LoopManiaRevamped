package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class Lifesteal extends Effect {

	private Battleable wielder;
	private double lifestealPercentage;

	/**
	 * Constructor.
	 * @param wielder - The entity that will gain the stolen health.
	 * @param lifestealPercentage - Between 0 - 1 (e.g. 0.8).
	 */
	public Lifesteal(Battleable wielder, double lifestealPercentage) {
		super(1, EffectTrigger.ON_HIT);
		this.wielder = wielder;
		this.lifestealPercentage = lifestealPercentage;
	}

	@Override
	public void useEffect() {
		Battleable target = getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		double stolenHealth = attr.getHealth() * lifestealPercentage;
		wielder.getBattleAttributes().addHealth(stolenHealth);
	}

	@Override
	public void setupEffect() {
		return;
	}

	@Override
	public Effect copyEffect() {
		return new Lifesteal(wielder, lifestealPercentage);
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitLifestealEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Lifesteal: %f, Uses: %d]", getClass().getSimpleName(), lifestealPercentage, getUses())
		);
	}
	
}
