package unsw.loopmania.items;

import java.util.Random;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.TranceEffect;
import unsw.loopmania.inventory.EquipmentType;

public class Staff extends EquipmentItem {
	private final Random rand = new Random();

	public Staff() {
		super(20, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/staff.png");
	}

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	/**
	 * 80% of inflicting trance upon the enemy.
	 */
	@Override
	public void modifyOutgoingAttack(Attack attack) {
		if (rand.nextDouble() < 0.3) {
			attack.addEffect(new TranceEffect(null));
		}
	}

	@Override
	public Item copyItem() {
		return new Staff();
	}
	
}
