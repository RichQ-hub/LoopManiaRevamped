package unsw.loopmania.combatants;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.battle.loot.LootTable;
import unsw.loopmania.entity.MovingEntity;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.observers.LocationObserver;
import unsw.loopmania.observers.LocationPublisher;

/**
 * A basic form of enemy in the world.
 */
public abstract class Enemy extends MovingEntity implements Battleable, LocationPublisher<Enemy> {

	private BattleAttributes battleAttributes;
	private LootTable lootTable;
	private List<LocationObserver<Enemy>> locationObservers;

	public Enemy(PathPosition position) {
        super(position);
		this.locationObservers = new ArrayList<>();
    }

	public abstract void specialAttack(Attack attack);

	// ==================================================================================
	// Battleable Methods.
	// ==================================================================================

	@Override
	public void attackOpponents(List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound) {
		battleAttributes.attackOpponents(battleEntities, battleEntitiesInRound);
	}

	@Override
	public Attack buildAttack() {
		Attack attack = battleAttributes.buildAttack();

		// TODO: Could move this inside the build attack method, AND add it as a method inside Battleable interface.
		// This is because we might want to apply modifiers AFTER we add special attack effects.
		specialAttack(attack);

		return attack;
	}

	@Override
	public void takeAttack(Attack attack, List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound) {
		battleAttributes.takeAttack(attack);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		manager.addEnemy(this);
	}

	@Override
	public void printInfo() {
		battleAttributes.printBattleAttributesInfo();
	}

	@Override
	public List<Battleable> getEntitiesToAttack(List<Battleable> battleEntities) {
		BattleState state = battleAttributes.getBattleState();
		return state.getOpponents(battleEntities);
	}

	@Override
	public boolean isAlive() {
		return battleAttributes.getHealth() > 0;
	}

	@Override
	public boolean isEnemy() {
		return battleAttributes.getBattleState().isEnemy();
	}

	@Override
	public boolean isWithinBattleRadius(Character character) {
		return battleAttributes.isWithinBattleRadius(character);
	}

	@Override
	public boolean isWithinSupportRadius(Character character) {
		return battleAttributes.isWithinSupportRadius(character);
	}

	@Override
	public Loot dropLoot() {
		return lootTable.dropLoot();
	}

	// ==================================================================================
	// Observer Methods.
	// ==================================================================================

	@Override
	public void subscribe(LocationObserver<Enemy> observer) {
		locationObservers.add(observer);
	}

	@Override
	public void unsubscribe(LocationObserver<Enemy> observer) {
		locationObservers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (LocationObserver<Enemy> o : locationObservers) {
			o.update(this);
		}
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public List<LocationObserver<Enemy>> getLocationObservers() {
		return locationObservers;
	}

	public void setLocationObservers(List<LocationObserver<Enemy>> locationObservers) {
		this.locationObservers = locationObservers;
	}

	public BattleAttributes getBattleAttributes() {
		return battleAttributes;
	}

	public void setBattleAttributes(BattleAttributes battleAttributes) {
		this.battleAttributes = battleAttributes;
	}

	public LootTable getLootTable() {
		return lootTable;
	}

	public void setLootTable(LootTable lootTable) {
		this.lootTable = lootTable;
	}
}
