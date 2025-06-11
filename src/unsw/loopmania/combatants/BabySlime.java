package unsw.loopmania.combatants;

import java.util.List;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.EnemyState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.loot.LootTable;

public class BabySlime extends Enemy {

	private Slime parent;
	private int duration;

	public BabySlime(PathPosition position, Slime parent) {
		super(position);
		this.parent = parent;
		this.duration = 2;
		// Note: Contains no image.

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 30, 0, 0, new EnemyState());
		attr.addBaseAttackEffect(new DamageEffect(4));
		super.setBattleAttributes(attr);

		// Set Loot Table.
		LootTable lootTable = new LootTable(0, 0);
		super.setLootTable(lootTable);
	}

	@Override
	public void takeAttack(Attack attack, List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound) {
		super.takeAttack(attack, battleEntities, battleEntitiesInRound);

		duration--;

		// If the baby slime was not killed within 2 hits, then we revive the parent slime which
		// will appear in the next round.
		if (duration == 0 && parent.isAnyBabiesAlive() && !parent.isRevived()) {
			BattleAttributes attr = parent.getBattleAttributes();
			attr.setHealth(attr.getMaxHealth());
			battleEntities.add(parent);
			battleEntities.removeAll(parent.getBabySlimes());
			parent.setRevived(true);
		}
	}

	@Override
	public void move() {
		return;
	}

	@Override
	public void specialAttack(Attack attack) {
		return;
	}
	
}
