package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.BarracksBuilding;
import unsw.loopmania.buildings.Building;

public class BarracksCard extends Card {

	public BarracksCard() {
		super.setEntityImageByPath("src/images/barracks_card.png");
	}

	@Override
	public boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY) {
		return world.isOnPath(buildingX, buildingY) && !world.hasExistingBuilding(buildingX, buildingY);
	}

	@Override
	public Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY) {
		Pair<Integer, Integer> buildingPos = new Pair<Integer, Integer>(buildingX, buildingY);

		BarracksBuilding barracks = new BarracksBuilding(buildingPos);
		return barracks;
	}

	@Override
	public Card copyCard() {
		return new BarracksCard();
	}
	
}
