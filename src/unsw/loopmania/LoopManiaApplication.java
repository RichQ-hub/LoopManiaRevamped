package unsw.loopmania;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unsw.loopmania.maps.GameMap;

/**
 * The main application. Run main method from this class.
 */
public class LoopManiaApplication extends Application {
    /**
     * The controller for the game. Stored as a field so can terminate it when click exit button.
     */
    private LoopManiaWorldController mainController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // Prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // Load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

		// Load the victory screen.
		VictoryMenuController victoryMenuController = new VictoryMenuController();
        FXMLLoader victoryMenuLoader = new FXMLLoader(getClass().getResource("VictoryMenuView.fxml"));
        victoryMenuLoader.setController(victoryMenuController);
        Parent victoryMenuRoot = victoryMenuLoader.load();

		// Load the game over screen.
		GameOverController gameOverController = new GameOverController();
        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        gameOverLoader.setController(gameOverController);
        Parent gameOverRoot = gameOverLoader.load();

        // Create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);

		// Adding css stylesheet to the scene.
		// scene.getStylesheets().add(getClass().getResource("css/GameView.css").toExternalForm());
        
        // Set functions which are activated when button click to switch menu is pressed.
        // e.g. from main menu to start the game, or from the game to return to main menu

		// Switch from victory screen to main menu.
		victoryMenuController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});

		// Switches from the menu to the game.
        mainMenuController.setGameSwitcher(() -> {

			GameMap selectedMap = mainMenuController.getSelectedMap();
			if (selectedMap == null) {
				return;
			}

			// Load the map and its controller inside loopManiaLoader.
			LoopManiaWorldControllerLoader loopManiaLoader = null;
			try {
				loopManiaLoader = new LoopManiaWorldControllerLoader(selectedMap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// Try and obtain the controller for the map.
			try {
				assert loopManiaLoader != null;
				this.mainController = loopManiaLoader.loadController();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Obtain the LoopManiaView.
			FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));

			// Attach the LoopManiaController with the LoopManiaView so they are connected.
			gameLoader.setController(mainController);

			// Try and load the object hierarchy from the fxml view.
			Parent gameRoot = null;
			try {
				gameRoot = gameLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Set switch menu handler from the game to the menu.
        	mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});

			// Set handler for switching from game to victory screen.
			mainController.setVictoryMenuSwitcher(() -> {switchToRoot(scene, victoryMenuRoot, primaryStage);});

			// Set handler for switching from game to game over screen.
			mainController.setGameOverSwitcher(() -> {switchToRoot(scene, gameOverRoot, primaryStage);});

			// Deploy the main onto the stage.
        	gameRoot.requestFocus();

			// Switch the scene from the main menu to the game.
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // wrap up activities when exit program
		if (mainController != null) {
			mainController.terminate();
		}
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage) {
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
