package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

/**
 * A goal node which represents the nodes in the composite pattern.
 */
public interface Goal {
	public boolean achievedGoal(LoopManiaWorld world);
	public String prettyPrint();
}
