package unsw.loopmania.combatants;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.AlliedState;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.entity.MovingEntity;
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

	private BattleAttributes battleAttributes;
    private IntegerProperty gold;
    private IntegerProperty exp;
	private DoubleProperty health;

	private List<LocationObserver<Character>> locationObservers;
    
    public Character(PathPosition position) {
		super(position);
        this.gold = new SimpleIntegerProperty(0);
        this.exp = new SimpleIntegerProperty(0);
		super.setEntityImageByPath("src/images/human_new.png");
		this.locationObservers = new ArrayList<>();

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 100, 0, 0, new AlliedState());
		attr.addAttackEffect(new DamageEffect(null, 15));

		this.battleAttributes = attr;

		// Bind health property.

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

	// ==================================================================================
	// Battleable Methods.
	// ==================================================================================

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
		return false;
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
		return;
	}

	@Override
	public Loot dropLoot() {
		return null;
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
    
}
