package unsw.loopmania.battle.battleState;

import java.util.List;

import unsw.loopmania.battle.Battleable;

public interface BattleState {
	public List<Battleable> getOpponents(List<Battleable> battleEntities);
	public boolean isEnemy();
}
