package unsw.loopmania.items;

import java.util.Random;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.TranceEffect;
import unsw.loopmania.inventory.EquipmentType;

public class Staff extends EquipmentItem {

	private static final int VALUE = 20;
	private static final String DESCRIPTION = new StringBuilder()
		.append("A melee weapon with very low stats (lower than both the sword and stake), which has ")
		.append("a random chance of inflicting a trance, which transforms the attacked enemy into an ")
		.append("allied soldier temporarily (and fights alongside the Character). If the trance ends ")
		.append("during the fight, the affected enemy reverts back to acting as an enemy which fights ")
		.append("the Character. If the fight ends whilst the enemy is in a trance, the enemy dies.")
		.toString();

	public Staff() {
		super(VALUE, DESCRIPTION, EquipmentType.Weapon);
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
		Random rand = new Random();
		if (rand.nextDouble() < 0.3) {
			attack.addEffect(new TranceEffect());
		}
	}

	@Override
	public Item copyItem() {
		return new Staff();
	}
	
}
