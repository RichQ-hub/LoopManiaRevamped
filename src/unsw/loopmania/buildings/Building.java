package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.entity.Battleable;
import unsw.loopmania.entity.StaticEntity;

public abstract class Building extends StaticEntity implements Battleable {

	public Building(Pair<Integer, Integer> position) {
		super(position);
	}
	
}
