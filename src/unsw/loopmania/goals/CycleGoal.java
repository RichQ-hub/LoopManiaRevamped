package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

public class CycleGoal implements Goal {
	private int cycleGoal;

	public CycleGoal(int cycleGoal) {
		this.cycleGoal = cycleGoal;
	}

	@Override
	public boolean achievedGoal(LoopManiaWorld world) {
		return world.getCycleCount() >= cycleGoal;
	}

	@Override
	public String prettyPrint() {
		return "Cycle: " + cycleGoal;
	}
}
