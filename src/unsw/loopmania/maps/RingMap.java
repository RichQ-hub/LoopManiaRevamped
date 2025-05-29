package unsw.loopmania.maps;

public class RingMap implements GameMap {
	private String gameMapName;
	private String gameMapFileName;
	// private Goal goal;

	public RingMap() {
		this.gameMapName = "Ring Map";
		this.gameMapFileName = "basic_world_with_player.json";
	}

	@Override
	public String getMapName() {
		return gameMapName;
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
	
}
