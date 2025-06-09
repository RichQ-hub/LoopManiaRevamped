package unsw.loopmania.battle.effects;

import java.util.List;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
import unsw.loopmania.battle.effects.modifiers.TriggerVampireBite;

/**
 * Triggers the VampireBite effect.
 */
public class VampireHit extends Effect {

	public VampireHit() {
		super(1, EffectTrigger.ON_HIT);
	}

	@Override
	public void useEffect() {
		Battleable target = getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.applyModifiers(List.of(new TriggerVampireBite()));
	}

	@Override
	public void setupEffect() {
		return;
	}

	@Override
	public Effect copyEffect() {
		return new VampireHit();
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitVampireHitEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s", getClass().getSimpleName())
		);
	}
	
}
