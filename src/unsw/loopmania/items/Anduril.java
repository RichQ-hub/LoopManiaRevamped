package unsw.loopmania.items;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.AndurilEffect;
import unsw.loopmania.battle.effects.modifiers.AddDamage;
import unsw.loopmania.inventory.EquipmentType;

public class Anduril extends EquipmentItem {

	private static final int VALUE = 70;
	private static final String DESCRIPTION = "A very high damage sword which causes triple damage against bosses.";
	private static final double DMG = 5; 

	public Anduril() {
		super(VALUE, DESCRIPTION, EquipmentType.Weapon);
		super.setEntityImageByPath("src/images/anduril_flame_of_the_west.png");
	}

	@Override
	public void modifyIncomingAttack(Attack attack) {
		return;
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		attack.applyModifier(new AddDamage(DMG));
		attack.addEffect(new AndurilEffect());

	}

	@Override
	public Item copyItem() {
		return new Anduril();
	}
	
}
