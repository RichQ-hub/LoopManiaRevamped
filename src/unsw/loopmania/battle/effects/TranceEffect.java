package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.AlliedState;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

/**
 * Trance effect lasts for 3 attacks on the entity it is inflicted upon.
 */
public class TranceEffect extends Effect {

	public TranceEffect() {
		super(3, EffectTrigger.ON_HIT);
	}

	@Override
	public void useEffect() {
		Battleable target = super.getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		if (getUses() == 1) {
			// We are on our last call, so we revert the enemy back to its enemy state.
			attr.setBattleState(new EnemyState());
		} else {
			attr.setBattleState(new AlliedState());
		}
	}

	@Override
	public Effect copyEffect() {
		return new TranceEffect();
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitTranceEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Uses: %d]", getClass().getSimpleName(), getUses())
		);
	}
	
}
