package unsw.loopmania.items;

import java.util.Random;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.VampireBiteModifier;
import unsw.loopmania.inventory.EquipmentType;

public class Shield extends EquipmentItem {

	private static final int VALUE = 20;
	private static final String DESCRIPTION = "Defends against enemy attacks, critical vampire attacks have a 60% lower chance of occurring.";

	public Shield() {
		super(VALUE, DESCRIPTION, EquipmentType.Shield);
		super.setEntityImageByPath("src/images/shield.png");
	}

	/**
	 * 60% of blocking a vampire critical bite.
	 */
	@Override
	public void modifyIncomingAttack(Attack attack) {
		Random rand = new Random();

		if (rand.nextDouble() < 0.6) {
			System.out.println("  * Blocking Vampire Bites");
			// Sets any incomving vampire bites to have uses = 0, essentially
			// blocking it.
			attack.applyModifier(new VampireBiteModifier(0));
		}
	}

	@Override
	public void modifyOutgoingAttack(Attack attack) {
		return;
	}

	@Override
	public Item copyItem() {
		return new Shield();
	}
}
