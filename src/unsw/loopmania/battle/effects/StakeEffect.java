package unsw.loopmania.battle.effects;

import java.util.ListIterator;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class StakeEffect extends Effect {

	private double dmg;

	public StakeEffect(Battleable target) {
		super(target, 1, EffectTrigger.ON_HIT);
		this.dmg = 0;
	}

	@Override
	public void useEffect(ListIterator<Effect> activeEffectsIterator) {
		Battleable target = super.getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.setHealth(attr.getHealth() - dmg);
	}

	@Override
	public Effect copyEffect() {
		return new StakeEffect(getTarget());
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitStakeEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Dmg: %f, Uses: %d]", getClass().getSimpleName(), dmg, getUses())
		);
	}

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}
	
}
