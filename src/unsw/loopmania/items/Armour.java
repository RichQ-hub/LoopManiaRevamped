package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.DamageMultiplier;
import unsw.loopmania.inventory.EquipmentType;

public class Armour extends EquipmentItem {

	public Armour() {
		super(30, EquipmentType.Chestpiece);
		super.setEntityImageByPath("src/images/armour.png");
	}

	/**
	 * Halve the incoming attack damage.
	 */
	@Override
	public void modifyIncomingAttack(Attack attack) {
		attack.applyModifier(new DamageMultiplier(0.5));
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		return;
	}

	@Override
	public Item copyItem() {
		return new Armour();
	}
	
}
