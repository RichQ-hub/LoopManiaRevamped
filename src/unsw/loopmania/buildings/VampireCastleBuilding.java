package unsw.loopmania.buildings;

import org.javatuples.Pair;

import unsw.loopmania.battle.BattleManager;

/**
 * A basic form of building in the world.
 */
public class VampireCastleBuilding extends Building {
    public VampireCastleBuilding(Pair<Integer, Integer> position) {
        super(position);
    }

	@Override
	public void addToBattleManager(BattleManager manager) {
		return;
	}
}
