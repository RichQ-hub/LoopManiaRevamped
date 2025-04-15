package unsw.loopmania.combatants;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleManager;
import unsw.loopmania.entity.MovingEntity;

//NOTE: HAVE BATTLE AS ITS OWN KEYFRAME IN THE CONTROLLER CLASS IN START TIMER METHOD.
//NOTE: PERHAPS HAVE A BATTLE CLASS THAT RUNS THE BATTLES AND STORED IN THE LOOPMANIAWORLD CLASS.
//NOTE: PERHAPS HAVE ENEMIES AND CHARACTERS ATTACK BY SENDING A ATTACK OBJECT WHICH CONTAINS ALL THE INFORMATION.

/**
 * represents the main character in the backend of the game world
 */
public class Character extends Combatant {

    private IntegerProperty gold;
    private IntegerProperty exp;

    //For now (Use builder pattern to set stats???????)
    private Object defenceStats;
    private Object attackStats;
    
    public Character(PathPosition position) {
		super(position, 100);
        this.gold = new SimpleIntegerProperty(0);
        this.exp = new SimpleIntegerProperty(0);
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
        this.gold.set(gold);;
    }

    public int getExp() {
        return exp.get();
    }

    public void setExp(int exp) {
        this.exp.set(exp);
    }

	@Override
	public void addToBattleManager(BattleManager manager) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addToBattleManager'");
	}

	@Override
	public void attack(Combatant opponent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'attack'");
	}

	@Override
	public void takeDamage(Attack attack) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'move'");
	}
    
}
