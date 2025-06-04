package unsw.loopmania.items;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.inventory.InventoryManager;

public class HealthPotion extends Item {

	public HealthPotion() {
		super(30);
		super.setEntityImageByPath("src/images/brilliant_blue_new.png");
	}

	@Override
	public Item copyItem() {
		return new HealthPotion();
	}

	@Override
	public void useItem(Character character, InventoryManager inventoryManager) {
		BattleAttributes attr = character.getBattleAttributes();
		attr.setHealth(attr.getHealth() + 10);
		destroy();
		inventoryManager.removeItemFromInventory(this);
	}
	
}
