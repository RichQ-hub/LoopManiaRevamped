package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class AndurilEffect extends Effect {

	// This effect will be disabled by default by having dmg = 0.
	private double dmg;

	public AndurilEffect() {
		super(1, EffectTrigger.ON_HIT);
		this.dmg = 0;
	}

	@Override
	public void useEffect() {
		Battleable target = getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.reduceHealth(dmg);
	}

	@Override
	public void setupEffect() {
		return;
	}

	@Override
	public Effect copyEffect() {
		return new AndurilEffect();
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitAndurilEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Dmg: %f, Uses: %d]", getClass().getSimpleName(), dmg, getUses())
		);
	}

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}
	
}
