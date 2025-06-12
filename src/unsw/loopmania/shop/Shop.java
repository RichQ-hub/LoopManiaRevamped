package unsw.loopmania.shop;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.inventory.InventoryManager;
import unsw.loopmania.items.Anduril;
import unsw.loopmania.items.Armour;
import unsw.loopmania.items.HealthPotion;
import unsw.loopmania.items.Helmet;
import unsw.loopmania.items.Item;
import unsw.loopmania.items.Shield;
import unsw.loopmania.items.Staff;
import unsw.loopmania.items.Stake;
import unsw.loopmania.items.Sword;
import unsw.loopmania.items.TheOneRing;
import unsw.loopmania.items.TreeStump;

public class Shop {
	private Character character;
	private InventoryManager inventoryManager;

	private List<Item> buyStock;

	public Shop(LoopManiaWorld world) {
		this.character = world.getCharacter();
		this.inventoryManager = world.getInventoryManager();
		this.buyStock = new ArrayList<>();

		// Set buyable items.
		buyStock.add(new Sword());
		buyStock.add(new Armour());
		buyStock.add(new Helmet());
		buyStock.add(new Shield());
		buyStock.add(new Stake());
		buyStock.add(new Staff());
		buyStock.add(new HealthPotion());
		buyStock.add(new TheOneRing());
		buyStock.add(new Anduril());
		buyStock.add(new TreeStump());
	}

	public Item buyItem(Item item) {
		if (character.getGold() < item.getValue()) {
			System.out.println("Insufficient Funds.");
			return null;
		}

		Item newCopy = item.copyItem();
		Item addedItem = inventoryManager.addItemToInventory(newCopy);
		if (addedItem == null) {
			// Inventory is full.
			return null;
		}

		// Reduce character gold.
		character.setGold(character.getGold() - item.getValue());

		return addedItem;
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

	public List<Item> getBuyStock() {
		return buyStock;
	}

	public void setBuyStock(List<Item> buyStock) {
		this.buyStock = buyStock;
	}
}
