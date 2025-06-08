package unsw.loopmania.battle.battleState;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.battle.Battleable;

public class StunnedState implements BattleState {

	private boolean prevIsEnemy;

	public StunnedState(boolean prevIsEnemy) {
		this.prevIsEnemy = prevIsEnemy;
	}

	@Override
	public List<Battleable> getOpponents(List<Battleable> battleEntities) {
		System.out.println("Cannot attack because I am stunned!");
		return new ArrayList<>();
	}

	@Override
	public boolean isEnemy() {
		return prevIsEnemy;
	}
	
}
