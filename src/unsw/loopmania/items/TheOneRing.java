package unsw.loopmania.items;

import unsw.loopmania.combatants.Character;
import unsw.loopmania.inventory.InventoryManager;

public class TheOneRing extends Item {

	private static final int VALUE = 50;
	private static final String DESCRIPTION = "If the Character is killed, it respawns with full health up to a single time.";

	public TheOneRing() {
		super(VALUE, DESCRIPTION);
		super.setEntityImageByPath("src/images/the_one_ring.png");
	}

	@Override
	public void useItem(Character character, InventoryManager inventoryManager) {
		
	}

	@Override
	public Item copyItem() {
		return new TheOneRing();
	}
	
}
