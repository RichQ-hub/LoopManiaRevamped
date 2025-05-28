package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.RemoveDamage;
import unsw.loopmania.inventory.EquipmentType;

public class Helmet extends EquipmentItem {

	public Helmet() {
		super(10, EquipmentType.Helmet);
		super.setEntityImageByPath("src/images/helmet.png");
	}

	/**
	 * Reduces incoming damage by 4.
	 */
	@Override
	public void modifyIncomingAttack(Attack attack) {
		attack.applyModifier(new RemoveDamage(4));
	}

	/**
	 * Reduces outgoing damage by 2 (since it is harder to see).
	 */
	@Override
	public void modifyOutgoingAttack(Attack attack) {
		attack.applyModifier(new RemoveDamage(2));
	}

	@Override
	public Item copyItem() {
		return new Helmet();
	}
	
}
