package unsw.loopmania.combatants;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
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

	// ==================================================================================
	// Battleable Methods.
	// ==================================================================================

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		manager.addEnemy(this);
	}

	@Override
	public void attack(Battleable combatant) {
		Attack attack = new Attack();
		
		List<Effect> attackEffects = battleAttributes.getAttackEffects();
		for (Effect e : attackEffects) {
			Effect copy = e.copyEffect();
			copy.setTarget(combatant);
			attack.addEffect(copy);
		}

		combatant.takeAttack(attack);
	}

	@Override
	public void takeAttack(Attack attack) {
		List<EffectModifier> defenseModifiers = battleAttributes.getDefenseModifiers();

		// Modify any incoming effects.
		for (EffectModifier m : defenseModifiers) {
			attack.applyModifier(m);
		}

		// Add all offensive effects onto the person.
		for (Effect e : attack.getEffects()) {
			battleAttributes.addActiveEffect(e);
		}

		// Trigger on-hit effects.
		battleAttributes.triggerEffects(Effect.EffectTrigger.ON_HIT);
	}

	@Override
	public void printInfo() {
		System.out.println(String.format("  Health: %f", getBattleAttributes().getHealth()));

		System.out.println("  Active Effects: {");
		for (Effect e : getBattleAttributes().getActiveEffects()) {
			e.printInfo();
		}
		System.out.println("  }");
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
		return true;
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
