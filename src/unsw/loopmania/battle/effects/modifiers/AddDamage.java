package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.DamageEffect;

public class AddDamage extends EffectModifier {
	private double addedDamage;

	public AddDamage(double addedDamage) {
		this.addedDamage = addedDamage;
	}

	@Override
	public void visitDamageEffect(DamageEffect effect) {
		effect.addDamage(addedDamage);
	}
}
