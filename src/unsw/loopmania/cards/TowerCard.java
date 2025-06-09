package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.buildings.TowerBuilding;

public class TowerCard extends Card {

	public TowerCard() {
		super.setEntityImageByPath("src/images/tower_card.png");
	}

	@Override
	public boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY) {
		return (
			!world.isOnPath(buildingX, buildingY) &&
			!world.hasExistingBuilding(buildingX, buildingY) &&
			world.isAdjacentToPath(buildingX, buildingY)
		);
	}

	@Override
	public Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY) {
		Pair<Integer, Integer> buildingPos = new Pair<Integer, Integer>(buildingX, buildingY);

		TowerBuilding tower = new TowerBuilding(buildingPos);
		return tower;
	}

	@Override
	public Card copyCard() {
		return new TowerCard();
	}
	
}
