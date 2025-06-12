package unsw.loopmania.combatants;

import java.util.ArrayList;
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

	private boolean isRevived;

	private List<BabySlime> babySlimes;

	public Slime(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/slime.png");

		// Custom variables.
		this.canSplit = true;
		this.isRevived = false;
		this.babySlimes = new ArrayList<>();
		babySlimes.add(new BabySlime(position, this));
		babySlimes.add(new BabySlime(position, this));

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 40, 4, 10, new EnemyState());
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
		
		if (getBattleAttributes().getHealth() <= 20 && canSplit) {
			// Split.
			battleEntities.addAll(babySlimes);
			battleEntities.remove(this);

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

	public boolean isAnyBabiesAlive() {
		return babySlimes.stream().anyMatch(s -> s.isAlive());
	}

	public boolean areAllBabiesExpired() {
		return babySlimes.stream().allMatch(s -> (s.getDuration() == 0));
	}

	public boolean isCanSplit() {
		return canSplit;
	}

	public void setCanSplit(boolean canSplit) {
		this.canSplit = canSplit;
	}

	public boolean isRevived() {
		return isRevived;
	}

	public void setRevived(boolean isRevived) {
		this.isRevived = isRevived;
	}

	public List<BabySlime> getBabySlimes() {
		return babySlimes;
	}

	public void setBabySlimes(List<BabySlime> babySlimes) {
		this.babySlimes = babySlimes;
	}
	
}
