package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.combatants.Character;
import unsw.loopmania.entity.StaticEntity;
import unsw.loopmania.inventory.InventoryManager;

public abstract class Item extends StaticEntity {

    private int value;
	private String description;

    public Item(int value, String description) {
        super(Pair.with(0, 0));
		this.value = value;
		this.description = description;
    }

	public abstract void useItem(Character character, InventoryManager inventoryManager);
	public abstract Item copyItem();

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
