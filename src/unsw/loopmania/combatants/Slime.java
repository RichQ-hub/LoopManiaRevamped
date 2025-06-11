package unsw.loopmania.combatants;

import java.util.List;
import java.util.Random;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.loot.LootItem;
import unsw.loopmania.battle.loot.LootTable;
import unsw.loopmania.items.Staff;

public class Slime extends Enemy {

	private final int goldReward = 10;
	private final int expReward = 10;

	// Can only split once.
	private boolean canSplit;

	public Slime(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/fire_dragon.png");

		// Custom variables.
		this.canSplit = true;

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 30, 4, 20, new EnemyState());
		attr.addBaseAttackEffect(new DamageEffect(7));

		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable(goldReward, expReward);
		lootTable.addLootItem(new LootItem(new Staff(), 100));
		super.setLootTable(lootTable);
	}

	@Override
	public void takeAttack(Attack attack, List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound) {
		super.takeAttack(attack, battleEntities, battleEntitiesInRound);
		
		if (getBattleAttributes().getHealth() <= 15 && canSplit) {
			// Split.

			this.canSplit = false;
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
