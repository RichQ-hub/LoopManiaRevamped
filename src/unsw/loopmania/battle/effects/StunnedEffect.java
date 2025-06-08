package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.battleState.StunnedState;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class StunnedEffect extends Effect {

	private BattleState prevBattleState;

	public StunnedEffect() {
		super(3, EffectTrigger.ON_ATTACK);
	}

	@Override
	public void useEffect() {
		Battleable target = getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		if (getUses() == 3) {
			this.prevBattleState = attr.getBattleState();
			attr.setBattleState(new StunnedState(prevBattleState.isEnemy()));
		} else if (getUses() == 1) {
			attr.setBattleState(prevBattleState);
		}
	}

	@Override
	public Effect copyEffect() {
		return new StunnedEffect();
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitStunnedEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Uses: %d]", getClass().getSimpleName(), getUses())
		);
	}
	
}
