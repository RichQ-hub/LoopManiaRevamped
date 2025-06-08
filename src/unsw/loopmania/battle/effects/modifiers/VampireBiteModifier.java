package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.VampireBite;

public class VampireBiteModifier extends EffectModifier {
	private int uses;

	public VampireBiteModifier(int uses) {
		this.uses = uses;
	}

	@Override
	public void visitVampireBiteEffect(VampireBite effect) {
		effect.setUses(uses);
		return;
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [New Uses: %d]", getClass().getSimpleName(), uses)
		);
	}
}
