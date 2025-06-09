package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class OneRingEffect extends Effect {

	public OneRingEffect() {
		super(-1, EffectTrigger.ON_DEATH);
	}

	@Override
	public void useEffect() {
		Battleable target = getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		if (attr.getHealth() < 0) {
			attr.setHealth(attr.getMaxHealth());
			setUses(0);
		}
	}

	@Override
	public void setupEffect() {
		return;
	}

	@Override
	public Effect copyEffect() {
		return new OneRingEffect();
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitOneRingEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Uses: %d]", getClass().getSimpleName(), getUses())
		);
	}
	
}
