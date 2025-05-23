package unsw.loopmania.effects.modifiers;

import unsw.loopmania.effects.DamageEffect;

public abstract class EffectModifier {
	public void visitDamageEffect(DamageEffect effect) {
		return;
	}

	// Add more visit functions as visit{effect_name}. All of these will return nothing.
	// Concrete modifiers thus can selectively choose which methods to override.
}
