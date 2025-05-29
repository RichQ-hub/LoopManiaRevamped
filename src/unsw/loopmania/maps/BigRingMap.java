package unsw.loopmania.maps;

public class BigRingMap implements GameMap {
	private String gameMapName;
	private String gameMapFileName;
	// private Goal goal;

	public BigRingMap() {
		this.gameMapName = "Big Ring Map";
		this.gameMapFileName = "big_ring_map.json";
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
