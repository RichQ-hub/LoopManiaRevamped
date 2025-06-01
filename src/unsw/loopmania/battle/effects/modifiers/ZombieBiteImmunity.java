package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.ZombieBite;

public class ZombieBiteImmunity extends EffectModifier {
	@Override
	public void visitZombieBiteEffect(ZombieBite effect) {
		effect.setUses(0);
	}
}
