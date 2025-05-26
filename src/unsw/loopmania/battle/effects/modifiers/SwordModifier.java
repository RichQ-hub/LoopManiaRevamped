package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.DamageEffect;

/**
 * Adds 3 dmg to the character's current dmg on attack.
 */
public class SwordModifier extends EffectModifier {
	@Override
	public void visitDamageEffect(DamageEffect effect) {
		effect.setDmg(effect.getDmg() + 3);
	}
}
