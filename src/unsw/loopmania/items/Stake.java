package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.StakeEffect;
import unsw.loopmania.inventory.EquipmentType;

public class Stake extends EquipmentItem {

	public Stake(Pair<Integer, Integer> position) {
		super(position, 20, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/stake.png");
	}

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		attack.addEffect(new StakeEffect(null));
	}

	@Override
	public Item copyItem() {
		return new Stake(Pair.with(0, 0));
	}

}
