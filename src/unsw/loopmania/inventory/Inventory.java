package unsw.loopmania.inventory;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.items.Item;

public class Inventory {
	private int inventoryWidth = 4;
	private int inventoryHeight = 4;

	private List<Item> items;

	public Inventory() {
		this.items = new ArrayList<>();
	}

	/**
	 * Adds an item into the unequipped inventory.
	 * @param item
	 * @return Item with new coordinates pertaining to its position in the unequipped inventory.
	 */
	public Item addItem(Item item) {
		if (item == null) {
			System.out.println("No item to add to inventory");
			return null;
		}
	
		Pair<Integer, Integer> position = getFirstAvailableSlotForItem();
	
		if (position == null) {
			System.out.println("Inventory is FULL");
			return null;
		}

		// NOTE: Even though the item might already have a set coordinates that it
		// was defined with, we ensure they are set with the new coordinates for
		// the current inventory.
		item.x().setValue(position.getValue0());
		item.y().setValue(position.getValue1());
		items.add(item);

		return item;
	}

	public void removeItemByCoordinates(int itemX, int itemY) {
		System.out.println("Are your removed?");
		int itemIdx = -1;
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
            if ((item.getX() == itemX) && (item.getY() == itemY)) {
				itemIdx = i;
				break;
            }
        }

		if (itemIdx == -1) {
			return;
		}

        items.remove(itemIdx);
	}

	public Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        // IMPORTANT - have to check by y first then x, since trying to find first available slot defined by looking row by row
        for (int y = 0; y < inventoryHeight; y++) {
            for (int x = 0; x < inventoryWidth; x++) {
                if (getInventoryItemByCoordinates(x, y) == null) {
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

	/**
     * Return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Item getInventoryItemByCoordinates(int itemX, int itemY) {
        for (Item item: this.items) {
            if ((item.getX() == itemX) && (item.getY() == itemY)) {
                return item;
            }
        }
        return null;
    }

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public int getInventoryWidth() {
		return inventoryWidth;
	}

	public void setInventoryWidth(int inventoryWidth) {
		this.inventoryWidth = inventoryWidth;
	}

	public int getInventoryHeight() {
		return inventoryHeight;
	}

	public void setInventoryHeight(int inventoryHeight) {
		this.inventoryHeight = inventoryHeight;
	}
}
