package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.StaticEntity;

public abstract class Item extends StaticEntity {

    private int sellPrice;
    private int buyPrice;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y, int sellPrice, int buyPrice) {
        super(x, y);
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    

    
}
