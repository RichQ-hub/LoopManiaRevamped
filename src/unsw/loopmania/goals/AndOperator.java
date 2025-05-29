package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;

public class AndOperator implements Goal {
	private Goal leftGoal;
    private Goal rightGoal;

	public AndOperator(Goal leftGoal, Goal rightGoal) {
		this.leftGoal = leftGoal;
		this.rightGoal = rightGoal;
	}

	@Override
	public boolean achievedGoal(LoopManiaWorld world) {
		return leftGoal.achievedGoal(world) && rightGoal.achievedGoal(world);
	}

	@Override
	public String prettyPrint() {
		return String.format("{%s AND %s}", leftGoal.prettyPrint(), rightGoal.prettyPrint());
	}
	
}
