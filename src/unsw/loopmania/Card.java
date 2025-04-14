package unsw.loopmania;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

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
