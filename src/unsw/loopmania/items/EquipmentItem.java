package unsw.loopmania.items;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class EquipmentItem extends Item {

    private int equipmentSlotCoords;
    private int equipmentStrategy;
    private int durability;

    public EquipmentItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int sellPrice, int buyPrice) {
        super(x, y, sellPrice, buyPrice);
    }

    public abstract int hello();
    
}
