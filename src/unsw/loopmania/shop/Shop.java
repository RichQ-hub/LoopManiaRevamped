package unsw.loopmania.shop;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.inventory.InventoryManager;
import unsw.loopmania.items.Item;

public class Shop {
	private Character character;

	private InventoryManager inventoryManager;

	public Shop(LoopManiaWorld world) {
		this.character = world.getCharacter();
		this.inventoryManager = world.getInventoryManager();
	}

	public void sellItem(Item item) {
		inventoryManager.removeItemFromInventory(item);
		item.destroy();
		character.setGold(character.getGold() + item.getValue());
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}
}
