package unsw.loopmania;

import org.javatuples.Pair;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends StaticEntity {
    // TODO = add more types of building, and make sure buildings have effects on entities as required by the spec
    public VampireCastleBuilding(Pair<Integer, Integer> position) {
        super(position);
    }
}
