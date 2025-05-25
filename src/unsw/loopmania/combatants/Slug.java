package unsw.loopmania.combatants;

import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.loot.LootCard;
import unsw.loopmania.battle.loot.LootItem;
import unsw.loopmania.battle.loot.LootTable;
import unsw.loopmania.cards.VampireCastleCard;
import unsw.loopmania.items.Sword;

public class Slug extends Enemy {

	public Slug(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/slug.png");

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 20, 4, 20, new EnemyState());
		attr.addAttackEffect(new DamageEffect(null, 2));

		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable();
		lootTable.addLootItem(new LootItem(new Sword(Pair.with(0, 0)), 50));
		lootTable.addLootCard(new LootCard(new VampireCastleCard(Pair.with(0, 0)), 100));
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

}
