package unsw.loopmania.maps;

import unsw.loopmania.goals.ExperienceGoal;
import unsw.loopmania.goals.Goal;

public class BigRingMap implements GameMap {
	private String gameMapName;
	private String gameMapFileName;
	private Goal goal;

	public BigRingMap() {
		this.gameMapName = "Big Ring Map";
		this.gameMapFileName = "big_ring_map.json";
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
		Goal expGoal = new ExperienceGoal(200);
		this.goal = expGoal;
	}
	
}
