package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.AddDamage;
import unsw.loopmania.inventory.EquipmentType;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends EquipmentItem {

	private static final int VALUE = 10;
	private static final String DESCRIPTION = "A standard melee weapon. Increases damage dealt by Character.";
    
    public Sword() {
        super(VALUE, DESCRIPTION, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/basic_sword.png");
    }

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	/**
	 * Deal an additional 3 damage to every attack.
	 */
	@Override
	public void modifyOutgoingAttack(Attack attack) {
		attack.applyModifier(new AddDamage(3));
	}

	@Override
	public Item copyItem() {
		return new Sword();
	}
}
