package unsw.loopmania.combatants;

import java.util.Random;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.ZombieBite;
import unsw.loopmania.battle.loot.LootItem;
import unsw.loopmania.battle.loot.LootTable;
import unsw.loopmania.items.Armour;

public class Zombie extends Enemy {

	private final int goldReward = 15;
	private final int expReward = 23;

	public Zombie(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/zombie.png");

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 30, 4, 10, new EnemyState());
		attr.addBaseAttackEffect(new DamageEffect(8));

		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable(goldReward, expReward);
		lootTable.addLootItem(new LootItem(new Armour(), 50));
		super.setLootTable(lootTable);
	}

	/**
	 * Zombies have a low chance of moving, hence they are slow.
	 */
	@Override
	public void move() {
		Random rand = new Random();
		if (rand.nextDouble() < 0.5) {
			moveUpPath();
		}
		notifyObservers();
	}

	@Override
	public void specialAttack(Attack attack) {
		Random rand = new Random();
		
		if (rand.nextDouble() < 0.4) {
			attack.addEffect(new ZombieBite());
		}
	}
}
