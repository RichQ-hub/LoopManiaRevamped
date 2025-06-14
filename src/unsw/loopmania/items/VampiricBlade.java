package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.Lifesteal;
import unsw.loopmania.battle.effects.modifiers.AddDamage;
import unsw.loopmania.inventory.EquipmentType;

public class VampiricBlade extends EquipmentItem {

	private static final int VALUE = 40;
	private static final String DESCRIPTION = "A demonic blade that saps 15% of the enemies health on every hit, adding it onto the wielder.";

	public VampiricBlade() {
		super(VALUE, DESCRIPTION, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/vampiric_blade.png");
	}

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		attack.applyModifier(new AddDamage(4));
		attack.addEffect(new Lifesteal(attack.getSender(), 0.15));
	}

	@Override
	public Item copyItem() {
		return new VampiricBlade();
	}
	
}
