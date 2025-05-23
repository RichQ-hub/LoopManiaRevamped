package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.entity.StaticEntity;

public abstract class Item extends StaticEntity {

    private int value;

    public Item(Pair<Integer, Integer> position, int value) {
        super(position);
		this.value = value;
    }

	// public Item(int value) {
    //     super(Pair.with(0, 0));
	// 	this.value = value;
    // }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public abstract Item copyItem();
    
}
