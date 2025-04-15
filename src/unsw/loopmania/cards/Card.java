package unsw.loopmania.cards;

import org.javatuples.Pair;

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
}
