package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.AndurilEffect;

public class AndurilProne extends EffectModifier {
	@Override
	public void visitAndurilEffect(AndurilEffect effect) {
		effect.setDmg(15);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s", getClass().getSimpleName())
		);
	}
}
