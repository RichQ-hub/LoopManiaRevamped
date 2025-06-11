package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.inventory.EquipmentType;

public class TreeStump extends EquipmentItem {

	private static final int VALUE = 50;
	private static final String DESCRIPTION = "An especially powerful shield, which provides higher defence against bosses.";

	public TreeStump() {
		super(VALUE, DESCRIPTION, EquipmentType.Shield);
		super.setEntityImageByPath("src/images/tree_stump.png");
	}

	@Override
	public void modifyIncomingAttack(Attack attack) {
		// TODO
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		return;
	}

	@Override
	public Item copyItem() {
		return new TreeStump();
	}
	
}
