package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.buildings.ZombiePitBuilding;

public class ZombiePitCard extends Card {

	public ZombiePitCard() {
		super.setEntityImageByPath("src/images/zombie_pit_card.png");
	}

	@Override
	public boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY) {
		return (
			!world.isOnPath(buildingX, buildingY) &&
			world.isAdjacentToPath(buildingX, buildingY) &&
			!world.hasExistingBuilding(buildingX, buildingY)
		);
	}

	@Override
	public Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY) {
		Pair<Integer, Integer> buildingPos = new Pair<Integer,Integer>(buildingX, buildingY);
		Pair<Integer, Integer> spawnPos = world.getAdjacentPath(buildingX, buildingY);

		return new ZombiePitBuilding(buildingPos, spawnPos, world);
	}

	@Override
	public Card copyCard() {
		return new ZombiePitCard();
	}
	
}
