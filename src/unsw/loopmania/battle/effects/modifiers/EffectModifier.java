package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.OneRingEffect;
import unsw.loopmania.battle.effects.StakeEffect;
import unsw.loopmania.battle.effects.TranceEffect;
import unsw.loopmania.battle.effects.VampireBite;
import unsw.loopmania.battle.effects.ZombieBite;

public abstract class EffectModifier {
	public void visitDamageEffect(DamageEffect effect) {
		return;
	}

	// Add more visit functions as visit{effect_name}. All of these will return nothing.
	// Concrete modifiers thus can selectively choose which methods to override.

	public void visitVampireBiteEffect(VampireBite effect) {
		return;
	}

	public void visitStakeEffect(StakeEffect effect) {
		return;
	}

	public void visitTranceEffect(TranceEffect effect) {
		return;
	}

	public void visitZombieBiteEffect(ZombieBite effect) {
		return;
	}

	public void visitOneRingEffect(OneRingEffect effect) {
		return;
	}
}
