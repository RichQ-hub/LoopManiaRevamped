package unsw.loopmania.inventory;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.items.EquipmentItem;
import unsw.loopmania.items.Item;

public class EquippedInventory {
	// Coord pairs are structured {x, y}.
	public static final EnumMap<EquipmentType, Pair<Integer, Integer>> SLOT_COORDS = new EnumMap<>(Map.ofEntries(
		Map.entry(EquipmentType.Helmet, new Pair<>(0, 0)),
		Map.entry(EquipmentType.Chestpiece, new Pair<>(1, 0)),
		Map.entry(EquipmentType.Shield, new Pair<>(2, 0)),
		Map.entry(EquipmentType.Weapon, new Pair<>(3, 0))
	));

	private List<EquipmentSlot> slots;

	public EquippedInventory() {
		this.slots = new ArrayList<>();
		this.slots.add(new EquipmentSlot(EquipmentType.Helmet, SLOT_COORDS.get(EquipmentType.Helmet)));
		this.slots.add(new EquipmentSlot(EquipmentType.Chestpiece, SLOT_COORDS.get(EquipmentType.Chestpiece)));
		this.slots.add(new EquipmentSlot(EquipmentType.Shield, SLOT_COORDS.get(EquipmentType.Shield)));
		this.slots.add(new EquipmentSlot(EquipmentType.Weapon, SLOT_COORDS.get(EquipmentType.Weapon)));
	}

	public Pair<EquipmentItem, Item> equipItem(EquipmentItem equipmentItem) {
		if (equipmentItem == null) {
			return null;
		}

		System.out.println(equipmentItem.getEquipmentType());

		for (EquipmentSlot slot : slots) {
			if (slot.getType() == equipmentItem.getEquipmentType()) {
				// Grab the old item that is already in the slot.
				System.out.println("Did you get here?");
				EquipmentItem oldItem = slot.getItem();

				// Set the new item into the slot.
				slot.setItem(equipmentItem);

				// Update the new coordinates for the equipped item to match the corresponding
				// cell in the frontend.
				equipmentItem.x().setValue(slot.getSlotX());
				equipmentItem.y().setValue(slot.getSlotY());

				return new Pair<EquipmentItem, Item>(equipmentItem, oldItem);
			}
		}
		System.out.println("oh no");
		return null;
	}

	public EquipmentItem removeEquippedItemByCoordinates(int slotX, int slotY) {
		for (EquipmentSlot slot : this.slots) {
			if ((slot.getSlotX() == slotX) && (slot.getSlotY() == slotY)) {
				EquipmentItem item = slot.getItem();
				slot.setItem(null);
				return item;
			}
		}
		return null;
	}

	public EquipmentItem getItemByType(EquipmentType type) {
		for (EquipmentSlot slot : this.slots) {
			if (slot.getType() == type) {
				return slot.getItem();
			}
		}
		return null;
	}


	// ==================================================================================
	// Attack Methods.
	// ==================================================================================

	/**
	 * Applies all equipment modifiers onto the incoming attack.
	 * @param attack
	 */
	public void modifyIncomingAttack(Attack attack) {
		for (EquipmentSlot slot : slots) {
			EquipmentItem equipment = slot.getItem();
			if (equipment != null) {
				equipment.modifyIncomingAttack(attack);
			}
		}
	}

	/**
	 * Applies all equipment modifiers onto the incoming attack.
	 * @param attack
	 */
	public void modifyOutgoingAttack(Attack attack) {
		for (EquipmentSlot slot : slots) {
			EquipmentItem equipment = slot.getItem();
			if (equipment != null) {
				equipment.modifyOutgoingAttack(attack);
			}
		}
	}

	public List<EquipmentSlot> getSlots() {
		return slots;
	}

	public void setSlots(List<EquipmentSlot> slots) {
		this.slots = slots;
	}
	
}
