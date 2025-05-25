package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class DamageEffect extends Effect {

	private double dmg;

	public DamageEffect(Battleable target, double dmg) {
		super(target, 1, EffectTrigger.ON_HIT);
		this.dmg = dmg;
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitDamageEffect(this);
	}

	@Override
	public void useEffect() {
		Battleable target = super.getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.setHealth(attr.getHealth() - dmg);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format(" - %s: [Dmg: %f, Uses: %d]", getClass().getSimpleName(), getDmg(), getUses())
		);
	}

	@Override
	public Effect copyEffect() {
		return new DamageEffect(null, dmg);
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================
	
	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}
}
