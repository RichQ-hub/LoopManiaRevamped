package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.SwordModifier;
import unsw.loopmania.inventory.EquipmentType;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends EquipmentItem {

	private static int value = 10;
    
    public Sword() {
        super(value, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/basic_sword.png");
    }

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		attack.applyModifier(new SwordModifier());
	}

	@Override
	public Item copyItem() {
		return new Sword();
	}
}
