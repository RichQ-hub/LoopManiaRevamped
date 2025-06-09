package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.VampireBite;

public class TriggerVampireBite extends EffectModifier {

	@Override
	public void visitVampireBiteEffect(VampireBite effect) {
		effect.activate();
		return;
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s", getClass().getSimpleName())
		);
	}
	
}
