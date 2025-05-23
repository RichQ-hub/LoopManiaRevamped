package unsw.loopmania.battle.loot;

import unsw.loopmania.items.Item;

public class LootItem {
	private Item item;
	private double dropChance;

	/**
	 * 
	 * @param item - Item to spawn.
	 * @param dropChance - Between 0 - 100
	 */
	public LootItem(Item item, double dropChance) {
		this.item = item;
		this.dropChance = dropChance;
	}

	public Item dropItem() {
		return item.copyItem();
	}

	public double getDropChance() {
		return dropChance;
	}
	
}
