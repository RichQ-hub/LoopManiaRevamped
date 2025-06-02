package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.TranceEffect;

public class TranceImmunity extends EffectModifier {
	@Override
	public void visitTranceEffect(TranceEffect effect) {
		effect.setUses(0);
	}
}
