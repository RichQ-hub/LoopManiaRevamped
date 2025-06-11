package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

/**
 * Occurs 40% of the time the vampire attacks, inflicting additional damage on
 * every subsequent hit (for a random number of hits) from a vampire ONLY.
 * Hence, this effect can only be triggered by the VampireHit effect, not
 * EffectTrigger.ON_HIT.
 */
public class VampireBite extends Effect {
	private double dmg;

	public VampireBite(int uses, double dmg) {
		super(uses, EffectTrigger.NONE);
		this.dmg = dmg;
	}

	/**
	 * Triggers the given effect onto the target.
	 */
	@Override
	public void useEffect() {
		Battleable target = super.getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.reduceHealth(dmg);
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
