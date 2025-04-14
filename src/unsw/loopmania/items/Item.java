package unsw.loopmania.items;

import org.javatuples.Pair;

import unsw.loopmania.StaticEntity;

public abstract class Item extends StaticEntity {

    private int value;

    public Item(Pair<Integer, Integer> position, int value) {
        super(position);
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
    
}
