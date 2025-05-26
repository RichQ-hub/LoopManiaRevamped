package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.VampireBite;

public abstract class EffectModifier {
	public void visitDamageEffect(DamageEffect effect) {
		return;
	}

	// Add more visit functions as visit{effect_name}. All of these will return nothing.
	// Concrete modifiers thus can selectively choose which methods to override.

	public void visitVampireBiteEffect(VampireBite effect) {
		return;
	}
}
