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
		attr.addBaseAttackEffect(new DamageEffect(8));
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
		battleAttributes.attackOpponents(battleEntities);
	}

	@Override
	public Attack buildAttack() {
		Attack attack = battleAttributes.buildAttack();
		
		// Apply equipment modifiers.
		EquippedInventory eInv = inventory.getEquippedInventory();
		eInv.modifyOutgoingAttack(attack);
		
		return attack;
	}

	@Override
	public void takeAttack(Attack attack) {
		// Modify incoming attack with equipment.
		EquippedInventory eInv = inventory.getEquippedInventory();
		eInv.modifyIncomingAttack(attack);

		battleAttributes.takeAttack(attack);
	}

	@Override
	public void printInfo() {
		battleAttributes.printBattleAttributesInfo();

		System.out.print("  EQUIPMENT: [");
		List<EquipmentSlot> slots = inventory.getEquippedInventory().getSlots();
		for (int i = 0; i < slots.size(); i++) {
			EquipmentItem item = slots.get(i).getItem();
			if (item == null) {
				System.out.print("*");
			} else {
				System.out.print(item.getClass().getSimpleName());
			}
			
			if (i < slots.size() - 1) {
				System.out.print(", ");
			}
		}
		System.out.print("]\n");

		// Print Allied Soldier count.
		System.out.println("  ALLIED SOLDIER COUNT: " + alliedSoldiers.size());
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
