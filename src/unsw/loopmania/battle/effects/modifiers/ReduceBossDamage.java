package unsw.loopmania.battle.effects.modifiers;

import unsw.loopmania.battle.effects.BossDamage;

public class ReduceBossDamage extends EffectModifier {

	private double amount;

	public ReduceBossDamage(double amount) {
		this.amount = amount;
	}

	@Override
	public void visitBossDamageEffect(BossDamage effect) {
		double newDmg = effect.getDmg() - amount;
		if (newDmg < 0) {
			newDmg = 0;
		}
		effect.setDmg(newDmg);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Removed Dmg: %f]", getClass().getSimpleName(), amount)
		);
	}
	
}
