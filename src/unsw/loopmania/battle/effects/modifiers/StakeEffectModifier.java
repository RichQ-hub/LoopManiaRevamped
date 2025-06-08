package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.StakeEffect;

public class StakeEffectModifier extends EffectModifier {

	private double newDmg;

	public StakeEffectModifier(double newDmg) {
		this.newDmg = newDmg;
	}

	@Override
	public void visitStakeEffect(StakeEffect effect) {
		effect.setDmg(newDmg);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [New Dmg: %f]", getClass().getSimpleName(), newDmg)
		);
	}
}
