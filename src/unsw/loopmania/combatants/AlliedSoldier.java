package unsw.loopmania.combatants;

import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.AlliedState;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.entity.StaticEntity;
import unsw.loopmania.managers.BattleManager;

public class AlliedSoldier extends StaticEntity implements Battleable {

	private BattleAttributes battleAttributes;

	public AlliedSoldier(Pair<Integer, Integer> position) {
		super(position);
		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 30, 0, 0, new AlliedState());
		attr.addBaseAttackEffect(new DamageEffect(7));

		this.battleAttributes = attr;
	}

	@Override
	public void attackOpponents(List<Battleable> battleEntities) {
		// Get opponents that are alive.
		List<Battleable> opponents = getEntitiesToAttack(battleEntities);
		for (Battleable opp : opponents) {
			System.out.println(String.format("\nAttacking -- {%s}: {%f}", opp.getClass().getSimpleName(), opp.getBattleAttributes().getHealth()));
			Attack attack = buildAttack();
			opp.takeAttack(attack);

			// Log info.
			opp.printInfo();
		}
	}

	@Override
	public Attack buildAttack() {
		Attack attack = new Attack();
		
		battleAttributes.insertBaseAttackEffects(attack);

		// DEBUG: Print attack.
		attack.printInfo("Base Attack");

		// Apply attack modifiers (buffs) the character might have.
		battleAttributes.modifyOutgoingAttack(attack); 

		// DEBUG: Print attack.
		attack.printInfo("Outgoing Attack");

		return attack;
	}

	@Override
	public void takeAttack(Attack attack) {
		// Modify any incoming effects.
		battleAttributes.modifyIncomingAttack(attack);

		// DEBUG: Print attack.
		attack.printInfo("Incoming Attack");

		// Add all offensive effects onto the person.
		for (Effect e : attack.getEffects()) {
			// Ensure the effect's target is this class.
			e.setTarget(this);
			battleAttributes.addActiveEffect(e);
		}

		// Trigger on-hit effects.
		battleAttributes.triggerEffects(Effect.EffectTrigger.ON_HIT);

		// Trigger death effects if health drops below 0.
		if (!isAlive()) {
			battleAttributes.triggerEffects(Effect.EffectTrigger.ON_DEATH);
		}
	}

	@Override
	public boolean isEnemy() {
		return battleAttributes.getBattleState().isEnemy();
	}

	@Override
	public boolean isAlive() {
		return battleAttributes.getHealth() > 0;
	}

	@Override
	public boolean isWithinBattleRadius(Character character) {
		return false;
	}

	@Override
	public boolean isWithinSupportRadius(Character character) {
		return false;
	}

	@Override
	public void move() {
		return;
	}

	@Override
	public Loot dropLoot() {
		return null;
	}

	@Override
	public void printInfo() {
		System.out.println(String.format("  Health: %f", getBattleAttributes().getHealth()));

		System.out.println("  Active Effects: {");
		for (Effect e : getBattleAttributes().getActiveEffects()) {
			e.printInfo();
		}
		System.out.println("  }");
	}

	@Override
	public List<Battleable> getEntitiesToAttack(List<Battleable> battleEntities) {
		BattleState state = battleAttributes.getBattleState();
		return state.getOpponents(battleEntities);
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		return;
	}

	@Override
	public BattleAttributes getBattleAttributes() {
		return battleAttributes;
	}

}
