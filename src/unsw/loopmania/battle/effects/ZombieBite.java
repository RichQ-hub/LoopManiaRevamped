package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.AlliedState;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.modifiers.AddDamage;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

/**
 * Units are converted to zombies (EnemyState) and last for 3 attacks. Zombified allies
 * cannot be tranced back.
 */
public class ZombieBite extends Effect {

	private AddDamage zombieBonusAttack;

	public ZombieBite() {
		super(3, EffectTrigger.ON_ATTACK);
		this.zombieBonusAttack = new AddDamage(3);
	}

	/**
	 * Turn the opponent into an enemy (e.g. Allied Soldier, etc) as well as give them a
	 * bonus 3 dmg on attack.
	 */
	@Override
	public void useEffect() {
		if (getUses() == 1) {
			Battleable target = super.getTarget();
			BattleAttributes attr = target.getBattleAttributes();
			attr.setBattleState(new AlliedState());
			attr.removeAttackModifier(zombieBonusAttack);
		}
	}

	@Override
	public void setupEffect() {
		Battleable target = getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		attr.setBattleState(new EnemyState());
		attr.addAttackModifier(zombieBonusAttack);
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
			String.format("	- %s: [Dmg Buff: %f, Uses: %d]", getClass().getSimpleName(), zombieBonusAttack.getAddedDamage(), getUses())
		);
	}
	
}
