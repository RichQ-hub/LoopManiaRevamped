package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public class Stake extends EquipmentItem {

    private static final int SELL_PRICE = 0;
    private static final int BUY_PRICE = 0;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, SELL_PRICE, BUY_PRICE);
        
    }

    @Override
    public int hello() {
        // TODO Auto-generated method stub
        return 20;
    }

    public static void main(String[] args) {
        Item stake = new Stake(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
    }

    
}
