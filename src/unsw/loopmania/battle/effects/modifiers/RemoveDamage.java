package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.DamageEffect;

public class RemoveDamage extends EffectModifier {
	private double removeDamage;

	public RemoveDamage(double removeDamage) {
		this.removeDamage = removeDamage;
	}

	@Override
	public void visitDamageEffect(DamageEffect effect) {
		effect.reduceDamage(removeDamage);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Removed Dmg: %f]", getClass().getSimpleName(), removeDamage)
		);
	}

	public double getRemoveDamage() {
		return removeDamage;
	}

	public void setRemoveDamage(double removeDamage) {
		this.removeDamage = removeDamage;
	}
}
