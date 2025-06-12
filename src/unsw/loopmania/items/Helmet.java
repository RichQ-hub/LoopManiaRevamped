package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.ReduceBossDamage;
import unsw.loopmania.battle.effects.modifiers.RemoveDamage;
import unsw.loopmania.inventory.EquipmentType;

public class Helmet extends EquipmentItem {

	private static final int VALUE = 10;
	private static final String DESCRIPTION = "Defends against enemy attacks, enemy attacks are reduced by a scalar value. The damage inflicted by the Character against enemies is reduced (since it is harder to see).";

	public Helmet() {
		super(VALUE, DESCRIPTION, EquipmentType.Helmet);
		super.setEntityImageByPath("src/images/helmet.png");
	}

	/**
	 * Reduces incoming damage by 4.
	 */
	@Override
	public void modifyIncomingAttack(Attack attack) {
		attack.applyModifier(new RemoveDamage(2));
		attack.applyModifier(new ReduceBossDamage(2));
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
