package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

/**
 * A goal node which represents the nodes in the composite pattern.
 */
public interface Goal {
	/**
	 * Returns whether a goal has been achieved.
	 * @param world
	 * @return
	 */
	public boolean achievedGoal(LoopManiaWorld world);

	/**
	 * String representation of the goal (or composite goal).
	 * @return
	 */
	public String prettyPrint();
}
