package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.combatants.Character;
import unsw.loopmania.entity.StaticEntity;
import unsw.loopmania.inventory.InventoryManager;

public abstract class Item extends StaticEntity {

    private int value;

    public Item(int value) {
        super(Pair.with(0, 0));
		this.value = value;
    }

	public abstract void useItem(Character character, InventoryManager inventoryManager);

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public abstract Item copyItem();
    
}
