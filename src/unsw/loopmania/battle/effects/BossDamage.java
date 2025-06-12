package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class BossDamage extends Effect {

	private double dmg;

	public BossDamage(double dmg) {
		super(1, EffectTrigger.ON_HIT);
		this.dmg = dmg;
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
		return new BossDamage(dmg);
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitBossDamageEffect(this);
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
