package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.inventory.EquipmentType;

public class Shield extends EquipmentItem {


	public Shield(Pair<Integer, Integer> position) {
		super(position, 20, EquipmentType.Shield);
		super.setEntityImageByPath("src/images/shield.png");
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

	
}
