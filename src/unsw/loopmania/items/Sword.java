package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.inventory.EquipmentType;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends EquipmentItem {

	private static int value = 10;
    
    public Sword(Pair<Integer, Integer> position) {
        super(position, value, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/basic_sword.png");
    }

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		return;
	}

	@Override
	public Item copyItem() {
		return new Sword(Pair.with(0, 0));
	}
}
