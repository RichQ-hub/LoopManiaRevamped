package unsw.loopmania.combatants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Pair;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.AlliedState;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
import unsw.loopmania.battle.effects.modifiers.ZombieBiteImmunity;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.entity.MovingEntity;
import unsw.loopmania.inventory.EquipmentSlot;
import unsw.loopmania.inventory.EquippedInventory;
import unsw.loopmania.inventory.InventoryManager;
import unsw.loopmania.items.EquipmentItem;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.observers.LocationObserver;
import unsw.loopmania.observers.LocationPublisher;

//NOTE: HAVE BATTLE AS ITS OWN KEYFRAME IN THE CONTROLLER CLASS IN START TIMER METHOD.
//NOTE: PERHAPS HAVE A BATTLE CLASS THAT RUNS THE BATTLES AND STORED IN THE LOOPMANIAWORLD CLASS.
//NOTE: PERHAPS HAVE ENEMIES AND CHARACTERS ATTACK BY SENDING A ATTACK OBJECT WHICH CONTAINS ALL THE INFORMATION.

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity implements Battleable, LocationPublisher<Character> {
    private IntegerProperty gold;
    private IntegerProperty exp;

	private BattleAttributes battleAttributes;
	private InventoryManager inventory;

	private IntegerProperty alliedSoldierCount;
	private ObservableList<AlliedSoldier> alliedSoldiers;

	private List<LocationObserver<Character>> locationObservers;

	public Character(PathPosition position) {
		super(position);
		super.setEntityImageByPath("src/images/human_new.png");
        this.gold = new SimpleIntegerProperty(0);
        this.exp = new SimpleIntegerProperty(0);
		this.locationObservers = new ArrayList<>();
		this.alliedSoldierCount = new SimpleIntegerProperty();

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 100, 0, 0, new AlliedState());
		attr.addBaseAttackEffect(new DamageEffect(15));
		attr.addDefenseModifier(new ZombieBiteImmunity());

		this.battleAttributes = attr;

		// Bind allied soldier count to list.
		this.alliedSoldiers = FXCollections.observableArrayList();
		this.alliedSoldierCount = new SimpleIntegerProperty();
		alliedSoldiers.addListener((ListChangeListener<AlliedSoldier>) c -> {
			this.alliedSoldierCount.set(alliedSoldiers.size());
		});
    }

	// ==================================================================================
	// Observer Methods.
	// ==================================================================================

	@Override
	public void subscribe(LocationObserver<Character> observer) {
		locationObservers.add(observer);
	}

	@Override
	public void unsubscribe(LocationObserver<Character> observer) {
		locationObservers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (LocationObserver<Character> o : locationObservers) {
			o.update(this);
		}
	}

	public void clearDestroyedObservers() {
		this.locationObservers = locationObservers.stream().filter(o -> o.shouldObserverExist()).collect(Collectors.toList()); 
	}

	// ==================================================================================
	// Battleable Methods.
	// ==================================================================================

	@Override
	public void attackOpponents(List<Battleable> battleEntities) {
		// Get opponents that are alive.
		List<Battleable> opponents = getEntitiesToAttack(battleEntities);
		for (Battleable opp : opponents) {
			System.out.println(String.format("\nAttacking -- {%s}: {%f}", opp.getClass().getSimpleName(), opp.getBattleAttributes().getHealth()));
			Attack attack = buildAttack();
			opp.takeAttack(attack);

			// Log info.
			opp.printInfo();
		}
	}

	@Override
	public Attack buildAttack() {
		Attack attack = new Attack();
		
		battleAttributes.insertBaseAttackEffects(attack);

		// DEBUG: Print attack.
		attack.printInfo("Base Attack");

		// Apply outgoing attack modifiers provided by equipped items.
		EquippedInventory eInv = inventory.getEquippedInventory();
		eInv.modifyOutgoingAttack(attack);

		// Apply attack modifiers (buffs) the character might have.
		battleAttributes.modifyOutgoingAttack(attack); 

		// DEBUG: Print attack.
		attack.printInfo("Outgoing Attack");

		return attack;
	}

	@Override
	public void takeAttack(Attack attack) {
		// Modify incoming attack with equipment defense modifiers.
		EquippedInventory eInv = inventory.getEquippedInventory();
		eInv.modifyIncomingAttack(attack);

		// Modify any incoming effects by the characters pre-existing defense modifiers.
		battleAttributes.modifyIncomingAttack(attack);

		// DEBUG: Print attack.
		attack.printInfo("Incoming Attack");

		// Add all offensive effects onto the person.
		for (Effect e : attack.getEffects()) {
			e.setTarget(this);
			battleAttributes.addActiveEffect(e);
		}

		// Trigger on-hit effects.
		battleAttributes.triggerEffects(Effect.EffectTrigger.ON_HIT);

		// Trigger death effects if health drops below 0.
		if (!isAlive()) {
			battleAttributes.triggerEffects(Effect.EffectTrigger.ON_DEATH);
		}
	}

	@Override
	public void printInfo() {
		System.out.println(String.format("  Health: %f", getBattleAttributes().getHealth()));

		// Print equipment.
		System.out.print("  Equipment: [");
		for (EquipmentSlot slot : inventory.getEquippedInventory().getSlots()) {
			EquipmentItem item = slot.getItem();
			if (item == null) {
				System.out.print("null");
			} else {
				System.out.print(item.getClass().getSimpleName());
			}
			System.out.print(", ");
		}
		System.out.print("]\n");

		// Print Active Effects.
		System.out.println("  Active Effects: {");
		for (Effect e : getBattleAttributes().getActiveEffects()) {
			e.printInfo();
		}
		System.out.println("  }");

		// Print Defense Modifiers.
		System.out.println("  Defense Modifiers: {");
		for (EffectModifier m : getBattleAttributes().getDefenseModifiers()) {
			System.out.println(m.getClass().getSimpleName());
		}
		System.out.println("  }");

		// Print Attack Modifiers.
		System.out.println("  Attack Modifiers: {");
		for (EffectModifier m : getBattleAttributes().getAttackModifiers()) {
			System.out.println(m.getClass().getSimpleName());
		}
		System.out.println("  }");

		// Print Allied Soldier count.
		System.out.println("  Allied Soldiers: " + alliedSoldiers.size());
	}

	@Override
	public boolean isWithinBattleRadius(Character character) {
		return false;
	}

	@Override
	public boolean isWithinSupportRadius(Character character) {
		return false;
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		return;
	}


	@Override
	public BattleAttributes getBattleAttributes() {
		return battleAttributes;
	}

	@Override
	public boolean isEnemy() {
		return battleAttributes.getBattleState().isEnemy();
	}

	@Override
	public boolean isAlive() {
		return battleAttributes.getHealth() > 0;
	}

	@Override
	public List<Battleable> getEntitiesToAttack(List<Battleable> battleEntities) {
		BattleState state = battleAttributes.getBattleState();
		return state.getOpponents(battleEntities);
	}

	@Override
	public void move() {
		moveDownPath();
		notifyObservers();
	}

	@Override
	public Loot dropLoot() {
		return null;
	}

	// ==================================================================================
	// AlliedSoldier Methods.
	// ==================================================================================

	public void addAlliedSoldier() {
		AlliedSoldier newSoldier = new AlliedSoldier(Pair.with(0, 0));
		this.alliedSoldiers.add(newSoldier);
	}

	public void removeDeadAlliedSoldiers() {
		List<AlliedSoldier> deadSoldiers = alliedSoldiers.stream().filter(s -> !s.isAlive()).collect(Collectors.toList());

		for (AlliedSoldier dead : deadSoldiers) {
			dead.destroy();
			alliedSoldiers.remove(dead);
		}
	}

	// ==================================================================================
	// Property Getters.
	// ==================================================================================

    public IntegerProperty getGoldProperty() {
        return gold;
    }

    public IntegerProperty getExpProperty() {
        return exp;
    }

	public IntegerProperty getSoldierCountProperty() {
		return alliedSoldierCount;
	}

    // ==================================================================================
	// Getters and Setters.
	// ==================================================================================

    public int getGold() {
        return gold.get();
    }

    public void setGold(int gold) {
        this.gold.set(gold);
    }

    public int getExp() {
        return exp.get();
    }

    public void setExp(int exp) {
        this.exp.set(exp);
    }

	public int getAlliedSoldierCount() {
        return alliedSoldierCount.get();
    }

    public void setAlliedSoldierCount(int count) {
        this.alliedSoldierCount.set(count);
    }

	public InventoryManager getInventory() {
		return inventory;
	}

	public void setInventory(InventoryManager inventory) {
		this.inventory = inventory;
	}

    public ObservableList<AlliedSoldier> getAlliedSoldiers() {
		return alliedSoldiers;
	}

	public void setAlliedSoldiers(ObservableList<AlliedSoldier> alliedSoldiers) {
		this.alliedSoldiers = alliedSoldiers;
	}

	public List<LocationObserver<Character>> getLocationObservers() {
		return locationObservers;
	}

	public void setLocationObservers(List<LocationObserver<Character>> locationObservers) {
		this.locationObservers = locationObservers;
	}
}
