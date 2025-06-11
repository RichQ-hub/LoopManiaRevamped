package unsw.loopmania.items;

import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.inventory.InventoryManager;

public class HealthPotion extends Item {

	private static final int VALUE = 30;
	private static final String DESCRIPTION = "Refills Character health by 10.";

	public HealthPotion() {
		super(VALUE, DESCRIPTION);
		super.setEntityImageByPath("src/images/brilliant_blue_new.png");
	}

	@Override
	public Item copyItem() {
		return new HealthPotion();
	}

	@Override
	public void useItem(Character character, InventoryManager inventoryManager) {
		BattleAttributes attr = character.getBattleAttributes();
		attr.addHealth(10);
		destroy();
		inventoryManager.removeItemFromInventory(this);
	}
	
}
