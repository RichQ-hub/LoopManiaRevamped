package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.AlliedState;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.modifiers.AddDamage;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class ZombieBite extends Effect {

	private AddDamage zombieBonusAttack;

	public ZombieBite() {
		super(3, EffectTrigger.ON_HIT);
		this.zombieBonusAttack = new AddDamage(3);
	}

	/**
	 * Turn the opponent into an enemy (e.g. Allied Soldier, etc) as well as give them a
	 * bonus 3 dmg on attack.
	 */
	@Override
	public void useEffect() {
		Battleable target = super.getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		if (getUses() == 3) {
			// We are on the first call, so on initial attack, which we use to convert
			// the target into an enemy and apply the bonus attack.
			attr.setBattleState(new EnemyState());
			attr.addAttackModifier(zombieBonusAttack);
		} else if (getUses() == 1) {
			attr.setBattleState(new AlliedState());
			attr.removeAttackModifier(zombieBonusAttack);
		}
	}

	@Override
	public Effect copyEffect() {
		return new ZombieBite();
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		modifier.visitZombieBiteEffect(this);
	}

	@Override
	public void printInfo() {
		System.out.println(
			String.format("	- %s: [Bonus Dmg: %f, Uses: %d]", getClass().getSimpleName(), zombieBonusAttack.getAddedDamage(), getUses())
		);
	}
	
}
