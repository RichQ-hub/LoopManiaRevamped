package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    IntegerProperty gold;
    IntegerProperty exp;
    
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
