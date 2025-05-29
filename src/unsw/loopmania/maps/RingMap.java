package unsw.loopmania.maps;

import unsw.loopmania.goals.Goal;
import unsw.loopmania.goals.GoldGoal;

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
		this.goal = goldGoal;
	}
}
