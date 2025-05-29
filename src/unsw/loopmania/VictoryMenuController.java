package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;

public class VictoryMenuController {
	/**
     * facilitates switching to main game
     */
    private MenuSwitcher mainMenuSwitcher;

	/**
     * Facilitates switching to main menu upon button click.
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        mainMenuSwitcher.switchMenu();
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        this.mainMenuSwitcher = mainMenuSwitcher;
    }
}
