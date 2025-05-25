package unsw.loopmania.battle.battleState;

import java.util.List;

import unsw.loopmania.battle.Battleable;

public class AlliedState implements BattleState {

	@Override
	public List<Battleable> getOpponents(List<Battleable> battleEntities) {
		List<Battleable> opponents = battleEntities
			.stream()
			.filter(e -> e.isEnemy())
			.toList();
		return opponents;
	}
	
}
