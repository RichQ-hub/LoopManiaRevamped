package unsw.loopmania.effects.modifiers;

import unsw.loopmania.effects.DamageEffect;

public class DamageEffectModifier extends EffectModifier {

	private double newDmg;

	public DamageEffectModifier(double newDmg) {
		this.newDmg = newDmg;
	}

	@Override
	public void visitDamageEffect(DamageEffect effect) {
		effect.setDmg(newDmg);
	}
}
