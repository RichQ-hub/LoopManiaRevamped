package unsw.loopmania.maps;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.loopmania.goals.AndOperator;
import unsw.loopmania.goals.CycleGoal;
import unsw.loopmania.goals.ExperienceGoal;
import unsw.loopmania.goals.Goal;
import unsw.loopmania.goals.GoldGoal;
import unsw.loopmania.goals.OrOperator;

public abstract class GameMap {
	private String gameMapName;
	private String gameMapFileName;

	public GameMap(String gameMapName, String gameMapFileName) {
		this.gameMapName = gameMapName;
		this.gameMapFileName = gameMapFileName;
	}

	/**
	 * Returns the canonical map name.
	 * @return
	 */
	public String getMapName() {
		return gameMapName;
	}

	/**
	 * Obtains the map's filename.
	 * @return
	 */
	public String getGameMapFilename() {
		return gameMapFileName;
	}

	/**
	 * Builds the binary tree of goals from the given goal object.
	 * Time Complexity: O(n).
	 * @param goals
	 * @return
	 */
	public Goal buildGoal(JSONObject jsonGoal) {
		Goal goal = null;
		String goalName = jsonGoal.getString("goal");
	
		if (goalName.equals("AND")) {
			JSONArray subgoals = jsonGoal.getJSONArray("subgoals");
			Goal leftGoal = buildGoal(subgoals.getJSONObject(0));
			Goal rightGoal = buildGoal(subgoals.getJSONObject(1));
			goal = new AndOperator(leftGoal, rightGoal);
		} else if (goalName.equals("OR")) {
			JSONArray subgoals = jsonGoal.getJSONArray("subgoals");
			Goal leftGoal = buildGoal(subgoals.getJSONObject(0));
			Goal rightGoal = buildGoal(subgoals.getJSONObject(1));
			goal = new OrOperator(leftGoal, rightGoal);
		} else if (goalName.equals("gold")) {
			int quantity = jsonGoal.getInt("quantity");
			goal = new GoldGoal(quantity);
		} else if (goalName.equals("experience")) {
			int quantity = jsonGoal.getInt("quantity");
			goal = new ExperienceGoal(quantity);
		} else if (goalName.equals("cycles")) {
			int quantity = jsonGoal.getInt("quantity");
			goal = new CycleGoal(quantity);
		}

		return goal;
	};
}
