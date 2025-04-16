package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.buildings.TrapBuilding;

public class TrapCard extends Card {

	public TrapCard(Pair<Integer, Integer> position) {
		super(position);
		super.setEntityImageByPath("src/images/trap_card.png");
	}

	@Override
	public boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY) {
		return (
			world.isOnPath(buildingX, buildingY) &&
			!world.hasExistingBuilding(buildingX, buildingY)
		);
	}

	@Override
	public Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY) {
		Pair<Integer, Integer> buildingPos = new Pair<Integer, Integer>(buildingX, buildingY);

		TrapBuilding trap = new TrapBuilding(buildingPos);
		return trap;
	}
	
}
