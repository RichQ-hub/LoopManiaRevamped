package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.DamageEffect;

public class DamageMultiplier extends EffectModifier {
	private double multiplier;

	public DamageMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	@Override
	public void visitDamageEffect(DamageEffect effect) {
		effect.setDmg(effect.getDmg() * multiplier);
		return;
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Multiplier: %f]", getClass().getSimpleName(), multiplier)
		);
	}
}
