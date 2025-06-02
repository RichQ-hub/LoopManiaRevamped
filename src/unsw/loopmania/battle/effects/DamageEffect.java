package unsw.loopmania.battle.effects;

import java.util.ListIterator;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class DamageEffect extends Effect {

	private double dmg;

	public DamageEffect(double dmg) {
		super(1, EffectTrigger.ON_HIT);
		this.dmg = dmg;
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitDamageEffect(this);
	}

	@Override
	public void useEffect(ListIterator<Effect> activeEffectsIterator) {
		Battleable target = super.getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.setHealth(attr.getHealth() - dmg);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Dmg: %f, Uses: %d]", getClass().getSimpleName(), dmg, getUses())
		);
	}

	@Override
	public Effect copyEffect() {
		return new DamageEffect(dmg);
	}

	public void reduceDamage(double dmg) {
		this.dmg = this.dmg - dmg;
		if (this.dmg < 0) {
			this.dmg = 0;
		}
	}

	public void addDamage(double dmg) {
		this.dmg = this.dmg + dmg;
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
