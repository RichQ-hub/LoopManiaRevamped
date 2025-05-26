package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.DamageEffect;

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
