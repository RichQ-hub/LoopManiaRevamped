package unsw.loopmania.maps;

import unsw.loopmania.goals.AndOperator;
import unsw.loopmania.goals.CycleGoal;
import unsw.loopmania.goals.Goal;
import unsw.loopmania.goals.GoldGoal;

public class OriginalMap implements GameMap {
	private String gameMapName;
	private String gameMapFileName;
	private Goal goal;

	public OriginalMap() {
		this.gameMapName = "Original Map";
		this.gameMapFileName = "world_with_twists_and_turns.json";
		buildGoal();
	}

	@Override
	public String getGameMapFilename() {
		return gameMapFileName;
	}

	@Override
	public Goal getGoal() {
		return goal;
	}

	@Override
	public String getMapName() {
		return gameMapName;
	}

	@Override
	public void buildGoal() {
		Goal goldGoal = new GoldGoal(700);
		Goal cycleGoal = new CycleGoal(10);

		this.goal = new AndOperator(goldGoal, cycleGoal);
	}
	
}
