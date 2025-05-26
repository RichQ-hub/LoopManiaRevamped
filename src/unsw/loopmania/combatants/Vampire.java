package unsw.loopmania.combatants;

import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.VampireBite;
import unsw.loopmania.battle.effects.modifiers.StakeEffectModifier;
import unsw.loopmania.battle.loot.LootItem;
import unsw.loopmania.battle.loot.LootTable;
import unsw.loopmania.items.Shield;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Sword;

public class Vampire extends Enemy {

	private final int goldReward = 20;
	private final int expReward = 30;

	public Vampire(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/vampire.png");

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 40, 4, 20, new EnemyState());
		attr.addAttackEffect(new DamageEffect(null, 7));
		attr.addDefenseModifier(new StakeEffectModifier(5)); // Weak to stake attacks.

		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable(goldReward, expReward);
		lootTable.addLootItem(new LootItem(new Sword(Pair.with(0, 0)), 50));
		lootTable.addLootItem(new LootItem(new Stake(Pair.with(0, 0)), 20));
		lootTable.addLootItem(new LootItem(new Shield(Pair.with(0, 0)), 20));
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
	}

	@Override
	public void specialAttack(Battleable combatant, Attack attack) {
		Random rand = new Random();

		// Number of uses for the effect between 2 - 4.
		int uses = rand.nextInt(3) + 2;

		// Do a random amount of damage between 10 - 15.
		int dmg = rand.nextInt(6) + 10;
		
		if (rand.nextDouble() < 0.4) {
			attack.addEffect(new VampireBite(combatant, uses, dmg));
		}
	}	
}
