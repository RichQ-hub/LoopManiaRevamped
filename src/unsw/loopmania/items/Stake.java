package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.StakeEffect;
import unsw.loopmania.inventory.EquipmentType;

public class Stake extends EquipmentItem {

	public Stake() {
		super(20, EquipmentType.Weapon);
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
