package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;

public class TrapCard extends Card {

	public TrapCard(Pair<Integer, Integer> position) {
		super(position);
		super.setEntityImageByPath("src/images/trap_card.png");
	}

	@Override
	public boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isValidDropLocation'");
	}

	@Override
	public Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createBuilding'");
	}
	
}
