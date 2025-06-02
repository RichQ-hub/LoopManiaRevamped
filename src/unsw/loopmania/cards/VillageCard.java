package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.buildings.VillageBuilding;

public class VillageCard extends Card {

	public VillageCard() {
		super.setEntityImageByPath("src/images/village_card.png");
	}

	@Override
	public boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY) {
		return world.isOnPath(buildingX, buildingY) && !world.hasExistingBuilding(buildingX, buildingY);
	}

	@Override
	public Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY) {
		Pair<Integer, Integer> buildingPos = new Pair<Integer, Integer>(buildingX, buildingY);

		VillageBuilding village = new VillageBuilding(buildingPos);
		return village;
	}

	@Override
	public Card copyCard() {
		return new VillageCard();
	}
	
}
