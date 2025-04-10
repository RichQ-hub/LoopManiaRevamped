package unsw.loopmania;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//NOTE: ZOMBIE INFECTION IS A WRAPPER CLASS THAT STORES THE ALLIED SOLDIER INSIDE IT.
//NOTE: HAVE BATTLE AS ITS OWN KEYFRAME IN THE CONTROLLER CLASS IN START TIMER METHOD.
//NOTE: PERHAPS HAVE A BATTLE CLASS THAT RUNS THE BATTLES AND STORED IN THE LOOPMANIAWORLD CLASS.
//NOTE: PERHAPS HAVE ENEMIES AND CHARACTERS ATTACK BY SENDING A ATTACK OBJECT WHICH CONTAINS ALL THE INFORMATION.

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {

    private IntegerProperty gold;
    private IntegerProperty exp;
    private List<String> activeEffects;

    //For now (Use builder pattern to set stats???????)
    private Object defenceStats;
    private Object attackStats;
    
    public Character(PathPosition position) {
        super(position);
        this.gold = new SimpleIntegerProperty(0);
        this.exp = new SimpleIntegerProperty(0);
    }

    //////////////////////////////////////////////////////////////////////////////////
    //                           Property Getters                                   //
    //////////////////////////////////////////////////////////////////////////////////

    public IntegerProperty getGoldProperty() {
        return gold;
    }

    public IntegerProperty getExpProperty() {
        return exp;
    }

    //////////////////////////////////////////////////////////////////////////////////
    //                           Getters and Setters                                //
    //////////////////////////////////////////////////////////////////////////////////

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
    
}
