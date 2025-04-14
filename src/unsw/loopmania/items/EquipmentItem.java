package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.inventory.EquipmentType;
import unsw.loopmania.inventory.EquippedInventory;

public abstract class EquipmentItem extends Item {

	private EquipmentType equipmentType;
	private Pair<Integer, Integer> slotCoords;

	public EquipmentItem(
		Pair<Integer, Integer> position,
		int value,
		EquipmentType equipmentType
	) {
		super(position, value);
		this.equipmentType = equipmentType;
		this.slotCoords = EquippedInventory.SLOT_COORDS.get(equipmentType);
	}

    public abstract void modifyIncomingAttack(Attack attack);
	public abstract void modifyOutgoingAttack(Attack attack);

	public boolean isValidEquipSlot(int x, int y) {
		return (x == slotCoords.getValue0()) && (y == slotCoords.getValue1());
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}
}
