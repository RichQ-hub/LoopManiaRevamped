package unsw.loopmania.maps;

public class OriginalMap implements GameMap {
	private String gameMapName;
	private String gameMapFileName;
	// private Goal goal;

	public OriginalMap() {
		this.gameMapName = "Original Map";
		this.gameMapFileName = "world_with_twists_and_turns.json";
	}

	@Override
	public String getGameMapFilename() {
		return gameMapFileName;
	}

	@Override
	public String getGoal() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getGoal'");
	}

	@Override
	public String getMapName() {
		return gameMapName;
	}
	
}
