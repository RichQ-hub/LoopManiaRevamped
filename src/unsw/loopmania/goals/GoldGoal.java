package unsw.loopmania.goals;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.combatants.Character;

public class GoldGoal implements Goal {
	private int goldGoal;

	public GoldGoal(int goldGoal) {
		this.goldGoal = goldGoal;
	}

	@Override
	public boolean achievedGoal(LoopManiaWorld world) {
		Character character = world.getCharacter();
		return character.getGold() >= goldGoal;
	}

	@Override
	public String prettyPrint() {
		return "Gold: " + goldGoal;
	}
	
}
