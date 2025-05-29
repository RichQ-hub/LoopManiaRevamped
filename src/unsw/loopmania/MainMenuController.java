package unsw.loopmania;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import unsw.loopmania.maps.BigRingMap;
import unsw.loopmania.maps.GameMap;
import unsw.loopmania.maps.OriginalMap;
import unsw.loopmania.maps.RingMap;

/**
 * controller for the main menu.
 * TODO = you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {

	@FXML
    private Label gameInfoLabel;

	@FXML
    private ChoiceBox<String> mapChoiceBox;

	@FXML
    private ChoiceBox<String> modeChoiceBox;

	/**
	 * LinkedHashMap preserves the order of keys when extracted using .keySet() method.
	 */
	private LinkedHashMap<String, GameMap> maps;

	private GameMap selectedMap;

	public MainMenuController() {
		this.maps = new LinkedHashMap<>();

		// Maps.
		GameMap originalMap = new OriginalMap();
		GameMap ringMap = new RingMap();
		GameMap bigRingMap = new BigRingMap();

		this.maps.put(originalMap.getMapName(), originalMap);
		this.maps.put(ringMap.getMapName(), ringMap);
		this.maps.put(bigRingMap.getMapName(), bigRingMap);

		// Initially the map is the original map.
		this.selectedMap = originalMap;
	}

    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

	/**
	 * Initialise function runs all the setup code AFTER the fxml view has loaded and this controller
	 * is attached to it.
	 */
	@FXML
	public void initialize() {
		gameInfoLabel.setText("Face off against endless foes in this strategic turn-based RPG. Gear up and construct buildings to stay alive.");
		setupGameMapOptions();
	}

	public void setupGameMapOptions() {
		List<String> mapNames = maps.keySet().stream().collect(Collectors.toList());
		mapChoiceBox.setItems(FXCollections.observableList(mapNames));
		mapChoiceBox.getSelectionModel().select(0);

		// Listeners for when an choice is selected.
		mapChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldV, newV) -> {
			this.selectedMap = maps.get(newV);
			System.out.println(selectedMap.getMapName());
		});
	}

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public GameMap getSelectedMap() {
		return selectedMap;
	}

	public void setSelectedMap(GameMap selectedMap) {
		this.selectedMap = selectedMap;
	}
}
