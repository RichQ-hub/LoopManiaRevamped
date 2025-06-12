package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.BossDamage;

public class BossDamageMultiplier extends EffectModifier {

	private double multiplier;

	public BossDamageMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	@Override
	public void visitBossDamageEffect(BossDamage effect) {
		effect.setDmg(effect.getDmg() * multiplier);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Multiplier: %f]", getClass().getSimpleName(), multiplier)
		);
	}
	
}
