package unsw.loopmania.combatants;

import java.util.Random;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.loot.LootTable;

public class ElanMuske extends Enemy {

	private final int goldReward = 200;
	private final int expReward = 300;

	public ElanMuske(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/ElanMuske.png");

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 100, 4, 20, new EnemyState());
		attr.addAttackEffect(new DamageEffect(20));

		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable(goldReward, expReward);
		super.setLootTable(lootTable);
	}

	@Override
	public void move() {
		int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        } else if (directionChoice == 1){
            moveDownPath();
        }

		notifyObservers();
	}

	@Override
	public void specialAttack(Battleable combatant, Attack attack) {
		return;
	}
	
}
