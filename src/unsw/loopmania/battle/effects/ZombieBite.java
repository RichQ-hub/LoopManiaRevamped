package unsw.loopmania.battle.effects;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.modifiers.AddDamage;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
import unsw.loopmania.battle.effects.modifiers.TranceImmunity;

/**
 * Units are converted to zombies (EnemyState) and last for 3 attacks. Zombified allies
 * cannot be tranced back.
 */
public class ZombieBite extends Effect {

	private BattleState prevBattleState;
	private AddDamage zombieBonusAttack;
	private TranceImmunity tranceImmunity;

	public ZombieBite() {
		super(3, EffectTrigger.ON_ATTACK);
		this.zombieBonusAttack = new AddDamage(3);
		this.tranceImmunity = new TranceImmunity();
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
			attr.setBattleState(prevBattleState);
			attr.removeAttackModifier(zombieBonusAttack);
			attr.removeDefenseModifier(tranceImmunity);
		}
	}

	@Override
	public void setupEffect() {
		Battleable target = getTarget();
		BattleAttributes attr = target.getBattleAttributes();
		this.prevBattleState = attr.getBattleState();
		attr.setBattleState(new EnemyState());
		attr.addAttackModifier(zombieBonusAttack);
		attr.addDefenseModifier(tranceImmunity);
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
