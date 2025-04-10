package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends EquipmentItem {

    private static final int SELL_PRICE = 0;
    private static final int BUY_PRICE = 0;
    
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, SELL_PRICE, BUY_PRICE);
    }

    @Override
    public int hello() {
        // TODO Auto-generated method stub
        return 0;
    }    
}
