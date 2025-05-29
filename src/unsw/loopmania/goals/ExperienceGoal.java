package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.combatants.Character;

public class ExperienceGoal implements Goal {
	private int expGoal;

	public ExperienceGoal(int expGoal) {
		this.expGoal = expGoal;
	}

	@Override
	public boolean achievedGoal(LoopManiaWorld world) {
		Character character = world.getCharacter();
		return character.getExp() >= expGoal;
	}

	@Override
	public String prettyPrint() {
		return "Exp: " + expGoal;
	}
}
