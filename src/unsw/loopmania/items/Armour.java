package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.BossDamageMultiplier;
import unsw.loopmania.battle.effects.modifiers.DamageMultiplier;
import unsw.loopmania.inventory.EquipmentType;

public class Armour extends EquipmentItem {

	private static final int VALUE = 30;
	private static final String DESCRIPTION = "Body armour, provides defence and halves enemy attack.";

	public Armour() {
		super(VALUE, DESCRIPTION, EquipmentType.Chestpiece);
		super.setEntityImageByPath("src/images/armour.png");
	}

	/**
	 * Halve the incoming attack damage.
	 */
	@Override
	public void modifyIncomingAttack(Attack attack) {
		attack.applyModifier(new DamageMultiplier(0.5));
		attack.applyModifier(new BossDamageMultiplier(0.5));
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
