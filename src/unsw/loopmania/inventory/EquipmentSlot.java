package unsw.loopmania.inventory;

import org.javatuples.Pair;

import unsw.loopmania.items.EquipmentItem;

public class EquipmentSlot {
	private EquipmentType type;
	private EquipmentItem item;
	private Pair<Integer, Integer> coords;

	public EquipmentSlot(EquipmentType type, Pair<Integer, Integer> coords) {
		this.type = type;
		this.item = null;
		this.coords = coords;
	}

	public EquipmentType getType() {
		return type;
	}

	public void setType(EquipmentType type) {
		this.type = type;
	}

	public EquipmentItem getItem() {
		return item;
	}

	public void setItem(EquipmentItem item) {
		this.item = item;
	}

	public int getSlotX() {
		return this.coords.getValue0();
	}

	public void setSlotX(int x) {
		this.coords.setAt0(Integer.valueOf(x));
	}
	
	public int getSlotY() {
		return this.coords.getValue1();
	}

	public void setSlotY(int y) {
		this.coords.setAt1(Integer.valueOf(y));
	}

}
