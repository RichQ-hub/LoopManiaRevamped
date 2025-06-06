package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.StakeEffect;
import unsw.loopmania.inventory.EquipmentType;

public class Stake extends EquipmentItem {

	private static final int VALUE = 20;
	private static final String DESCRIPTION = "A melee weapon with lower stats than the sword, but causes very high damage to vampires.";

	public Stake() {
		super(VALUE, DESCRIPTION, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/stake.png");
	}

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		attack.addEffect(new StakeEffect());
	}

	@Override
	public Item copyItem() {
		return new Stake();
	}

}
