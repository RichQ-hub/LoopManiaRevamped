package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

/**
 * Occurs 40% of the time the vampire attacks, inflicting the VampireBleed effect
 * which deals 10 - 15 damage on each subsequent hit
 * Inflicts the VampireBleed effect on the target which triggers on hit
 */
public class VampireBite extends Effect {
	private double dmg;

	public VampireBite(int uses, double dmg) {
		super(uses, EffectTrigger.ON_HIT);
		this.dmg = dmg;
	}

	/**
	 * Triggers the given effect onto the target.
	 */
	@Override
	public void useEffect() {
		Battleable target = super.getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.setHealth(attr.getHealth() - dmg);
	}

	@Override
	public void setupEffect() {
		return;
	}

	@Override
	public Effect copyEffect() {
		return new VampireBite(getUses(), dmg);
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitVampireBiteEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Dmg: %f, Uses: %d]", getClass().getSimpleName(), dmg, getUses())
		);
	}
}
