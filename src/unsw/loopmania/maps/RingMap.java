package unsw.loopmania.maps;

import unsw.loopmania.goals.AndOperator;
import unsw.loopmania.goals.CycleGoal;
import unsw.loopmania.goals.ExperienceGoal;
import unsw.loopmania.goals.Goal;
import unsw.loopmania.goals.GoldGoal;
import unsw.loopmania.goals.OrOperator;

public class RingMap implements GameMap {
	private String gameMapName;
	private String gameMapFileName;
	private Goal goal;

	public RingMap() {
		this.gameMapName = "Ring Map";
		this.gameMapFileName = "basic_world_with_player.json";
		buildGoal();
	}

	@Override
	public String getMapName() {
		return gameMapName;
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
	public void buildGoal() {
		Goal goldGoal = new GoldGoal(200);
		Goal expGoal = new ExperienceGoal(50);
		Goal cycleGoal = new CycleGoal(3);

		Goal orGoal = new OrOperator(expGoal, goldGoal);
		Goal andGoal = new AndOperator(cycleGoal, orGoal);

		this.goal = andGoal;
	}
}
