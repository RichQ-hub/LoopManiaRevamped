package unsw.loopmania.combatants;

import java.util.Random;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.BossDamage;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.StunnedEffect;
import unsw.loopmania.battle.effects.modifiers.AndurilProne;
import unsw.loopmania.battle.effects.modifiers.TranceImmunity;
import unsw.loopmania.battle.loot.LootTable;

public class Doggie extends Enemy {

	private final int goldReward = 200;
	private final int expReward = 300;

	public Doggie(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/doggie.png");

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 50, 4, 20, new EnemyState());
		attr.addBaseAttackEffect(new DamageEffect(8));
		attr.addBaseAttackEffect(new BossDamage(16));
		attr.addDefenseModifier(new TranceImmunity());
		attr.addDefenseModifier(new AndurilProne());

		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable(goldReward, expReward);
		super.setLootTable(lootTable);
	}
	
	@Override
	public void move() {
		int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0) {
            moveUpPath();
        } else if (directionChoice == 1) {
            moveDownPath();
        }

		notifyObservers();
	}

	/**
	 * 40% chance of inflicting the stunned effect, preventing the opponent from attacking for a number
	 * of battle rounds.
	 */
	@Override
	public void specialAttack(Attack attack) {
		Random rand = new Random();
		if (rand.nextDouble() < 0.4) {
			attack.addEffect(new StunnedEffect());
		}
	}


}
