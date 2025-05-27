package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.buildings.CampfireBuilding;

public class CampfireCard extends Card {

	public CampfireCard(Pair<Integer, Integer> position) {
		super(position);
		super.setEntityImageByPath("src/images/campfire_card.png");
	}

	@Override
	public boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY) {
		return (
			!world.isOnPath(buildingX, buildingY) &&
			!world.hasExistingBuilding(buildingX, buildingY)
		);
	}

	@Override
	public Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY) {
		Pair<Integer, Integer> buildingPos = new Pair<Integer, Integer>(buildingX, buildingY);

		CampfireBuilding camp = new CampfireBuilding(buildingPos);
		return camp;
	}

	@Override
	public Card copyCard() {
		return new CampfireCard(Pair.with(0, 0));
	}
	
}
