package unsw.loopmania.inventory;

import org.javatuples.Pair;

import unsw.loopmania.items.EquipmentItem;
import unsw.loopmania.items.Item;

public class InventoryManager {
	// Could change to combatant later on so that even enemies can equip items.
	// private Character character;

	private EquippedInventory equippedInventory;
	private Inventory inventory;

	public InventoryManager() {
		this.equippedInventory = new EquippedInventory();
		this.inventory = new Inventory();
	}

	/**
	 * Equips an item.
	 * @param itemX
	 * @param itemY
	 * @param slotX
	 * @param slotY
	 * @return {@code Pair} where:
	 * 		{@code EquipmentItem} The newly equipped item with updated coords to match the cell
	 * 		on the frontend.
	 * 		{@code Item} The old item from the equipped slot to be added back into the inventory.
	 */
	public Pair<EquipmentItem, Item> equipInventoryItemByCoordinates(int itemX, int itemY, int slotX, int slotY) {
		Item item = inventory.getInventoryItemByCoordinates(itemX, itemY);

		if (item != null && item instanceof EquipmentItem) {
			EquipmentItem equipment = (EquipmentItem) item;
			if (equipment.isValidEquipSlot(slotX, slotY)) {
				// Before equipping the item, remove the item from the inventory.
				inventory.removeItemByCoordinates(itemX, itemY);

				// Equip.
				Pair<EquipmentItem, Item> result = equippedInventory.equipItem(equipment);

				if (result == null) {
					System.out.println("Were you equipped: NAH");
					// If we couldn't equip it, put it back in the inventory.
					inventory.addItem(equipment);
					return null;
				}

				System.out.println("Were you equipped: YES");

				EquipmentItem newlyEquippedItem = result.getValue0();
				Item oldEquippedItem = result.getValue1();

				// Add the old item from the equipped slot back into the inventory and
				// return its updated coords.
				oldEquippedItem = inventory.addItem(oldEquippedItem);
				return new Pair<EquipmentItem, Item>(newlyEquippedItem, oldEquippedItem);
			}
		}

		return null;
	}

	public Item unequipEquipmentItemByCoordinates(int slotX, int slotY) {
		EquipmentItem item = equippedInventory.removeEquippedItemByCoordinates(slotX, slotY);
		Item newItem = inventory.addItem(item);
		if (newItem == null) {
			// If the inventory was full, equip it back.
			equippedInventory.equipItem(item);
			return null;
		}

		return newItem;
	}

	/**
	 * Adds an item into the unequipped inventory.
	 * @param item
	 * @return Item with new coordinates pertaining to its position in the unequipped inventory.
	 */
	public Item addItemToInventory(Item item) {
		return inventory.addItem(item);
	}

	public void removeItemFromInventory(Item item) {
		inventory.removeItem(item);
	}

	public boolean canEquipInventoryItemByCoordinates(int itemX, int itemY, int slotX, int slotY) {
		Item item = inventory.getInventoryItemByCoordinates(itemX, itemY);

		if (item != null && item instanceof EquipmentItem) {
			EquipmentItem equipment = (EquipmentItem) item;
			if (equipment.isValidEquipSlot(slotX, slotY)) {
				return true;
			}
		}

		return false;
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public EquippedInventory getEquippedInventory() {
		return equippedInventory;
	}

	public void setEquippedInventory(EquippedInventory equippedInventory) {
		this.equippedInventory = equippedInventory;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
