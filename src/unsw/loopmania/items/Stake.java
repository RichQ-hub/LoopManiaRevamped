package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.inventory.EquipmentType;

public class Stake extends EquipmentItem {

	public Stake(Pair<Integer, Integer> position) {
		super(position, 20, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/stake.png");
	}

	@Override
	public void modifyIncomingAttack(Attack attack) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'modifyIncomingAttack'");
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'modifyOutgoingAttack'");
	}

	@Override
	public Item copyItem() {
		return new Stake(Pair.with(0, 0));
	}

}
