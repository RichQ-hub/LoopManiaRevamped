package unsw.loopmania.maps;

import unsw.loopmania.goals.Goal;

public interface GameMap {
	public String getMapName();
	public String getGameMapFilename();
	public Goal getGoal();
	public void buildGoal();
}
