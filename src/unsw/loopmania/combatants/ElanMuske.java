package unsw.loopmania.combatants;

import java.util.List;
import java.util.Random;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.modifiers.AndurilProne;
import unsw.loopmania.battle.effects.modifiers.TranceImmunity;
import unsw.loopmania.battle.loot.LootTable;

public class ElanMuske extends Enemy {

	private final int goldReward = 200;
	private final int expReward = 300;

	public ElanMuske(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/ElanMuske.png");

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 60, 4, 20, new EnemyState());
		attr.addBaseAttackEffect(new DamageEffect(20));
		attr.addDefenseModifier(new TranceImmunity());
		attr.addDefenseModifier(new AndurilProne());

		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable(goldReward, expReward);
		super.setLootTable(lootTable);
	}

	@Override
	public void attackOpponents(List<Battleable> battleEntities) {
		// On attack opponents, Muske has a 30% chance of healing all enemies (IN BATTLE) by 5 health.
		super.attackOpponents(battleEntities);

		Random rand = new Random();
		if (rand.nextDouble() < 0.5) {
			System.out.println("  * Elan heals all enemies by 5 health!");
			List<Battleable> enemies = battleEntities.stream().filter(e -> e.isAlive() && isEnemy()).toList();
			for (Battleable enemy : enemies) {
				BattleAttributes attr = enemy.getBattleAttributes();
				attr.setHealth(attr.getHealth() + 5);
			}
		}
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

	@Override
	public void specialAttack(Attack attack) {
		return;
	}
	
}
