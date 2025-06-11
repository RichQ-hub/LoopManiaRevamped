package unsw.loopmania.maps;

import unsw.loopmania.goals.Goal;

public interface GameMap {
	/**
	 * Returns the canonical map name.
	 * @return
	 */
	public String getMapName();

	/**
	 * Obtains the map's file name.
	 * @return
	 */
	public String getGameMapFilename();

	/**
	 * Obtains the set goal for the current map.
	 * @return
	 */
	public Goal getGoal();

	/**
	 * Builds the goal for the map.
	 */
	public void buildGoal();
}
