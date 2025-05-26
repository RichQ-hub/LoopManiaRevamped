package unsw.loopmania.items;

import java.util.Random;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.effects.modifiers.VampireBiteModifier;
import unsw.loopmania.inventory.EquipmentType;

public class Shield extends EquipmentItem {

	public Shield(Pair<Integer, Integer> position) {
		super(position, 20, EquipmentType.Shield);
		super.setEntityImageByPath("src/images/shield.png");
	}

	/**
	 * 60% of blocking a vampire critical bite.
	 */
	@Override
	public void modifyIncomingAttack(Attack attack) {
		Random rand = new Random();

		if (rand.nextDouble() < 0.6) {
			System.out.println("  Blocking Vampire Bites");
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
		return new Shield(Pair.with(0, 0));
	}
}
