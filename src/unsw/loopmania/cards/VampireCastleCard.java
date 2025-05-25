package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.buildings.VampireCastleBuilding;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {

    public VampireCastleCard(Pair<Integer, Integer> position) {
        super(position);
		super.setEntityImageByPath("src/images/vampire_castle_card.png");
    }

	/**
	 * If the drop location is not on the path AND is adjacent to a path, then we can
	 * drop it there.
	 */
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
		Pair<Integer, Integer> buildingPos = new Pair<Integer, Integer>(buildingX, buildingY);
		Pair<Integer, Integer> spawnPos = world.getAdjacentPath(buildingX, buildingY);

		VampireCastleBuilding vampireCastle = new VampireCastleBuilding(buildingPos, spawnPos);
		return vampireCastle;
	}

	@Override
	public Card copyCard() {
		return new VampireCastleCard(Pair.with(0, 0));
	}
}
