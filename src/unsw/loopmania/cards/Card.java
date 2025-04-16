package unsw.loopmania.cards;

import org.javatuples.Pair;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.buildings.Building;
import unsw.loopmania.entity.StaticEntity;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    // TODO = implement other varieties of card than VampireCastleCard
    public Card(Pair<Integer, Integer> position) {
        super(position);
    }

	public abstract boolean isValidDropLocation(LoopManiaWorld world, int buildingX, int buildingY);
	public abstract Building createBuilding(LoopManiaWorld world, int buildingX, int buildingY);
}
