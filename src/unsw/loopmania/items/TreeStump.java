package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.ReduceBossDamage;
import unsw.loopmania.battle.effects.modifiers.RemoveDamage;
import unsw.loopmania.inventory.EquipmentType;

public class TreeStump extends EquipmentItem {

	private static final int VALUE = 50;
	private static final String DESCRIPTION = "An especially powerful shield, which provides higher defence against bosses.";

	public TreeStump() {
		super(VALUE, DESCRIPTION, EquipmentType.Shield);
		super.setEntityImageByPath("src/images/tree_stump.png");
	}

	/**
	 * Reduce both basic and boss dmg.
	 */
	@Override
	public void modifyIncomingAttack(Attack attack) {
		attack.applyModifier(new ReduceBossDamage(5));
		attack.applyModifier(new RemoveDamage(3));
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
