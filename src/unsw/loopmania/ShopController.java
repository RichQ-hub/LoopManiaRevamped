package unsw.loopmania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.items.Item;
import unsw.loopmania.shop.Shop;

public class ShopController {

	@FXML
	private GridPane equippedItems;

	@FXML
	private GridPane unequippedInventory;

	@FXML
	private Label healthLabel;

	@FXML
	private Label goldLabel;

	@FXML
	private Label expLabel;

	@FXML
	private Label alliedSoldierLabel;

	@FXML
	private Label cycleLabel;

	@FXML
	private VBox shopSellMenu;

	@FXML
	private VBox shopBuyMenu;

	@FXML
	private ScrollPane shopMenu;

	public enum ShopTab {
		BUY,
		SELL
	}

	private LoopManiaWorld world;
	private LoopManiaWorldController gameController;
	private MenuSwitcher gameSwitcher;
	private Shop shop;
	private List<ImageView> sellItemViews;

	public ShopController(LoopManiaWorldController gameController) {
		this.gameController = gameController;
		this.world = gameController.getWorld();
		this.shop = new Shop(world);
		this.sellItemViews = new ArrayList<>();
	}

	@FXML
    public void initialize() {
		// add the empty slot images for the unequipped inventory
		Image inventorySlotImg = gameController.loadImage("src/images/empty_slot.png");
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImg);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

		// Initialise equipped items gridpane
		Image helmetSlotImg = gameController.loadImage("src/images/helmet_slot.png");
		Image chestpieceSlotImg = gameController.loadImage("src/images/armour_unequipped.png");
		Image shieldSlotImg = gameController.loadImage("src/images/shield_unequipped.png");
		Image weaponSlotImg = gameController.loadImage("src/images/sword_unequipped.png");
		ImageView helmetSlotView = new ImageView(helmetSlotImg);
		ImageView chestpieceSlotView = new ImageView(chestpieceSlotImg);
		ImageView shieldSlotView = new ImageView(shieldSlotImg);
		ImageView weaponSlotView = new ImageView(weaponSlotImg);
		equippedItems.add(helmetSlotView, 0, 0);
		equippedItems.add(chestpieceSlotView, 1, 0);
		equippedItems.add(shieldSlotView, 2, 0);
		equippedItems.add(weaponSlotView, 3, 0);

		// Bind Label Properties.
		Character character = world.getCharacter();
		healthLabel.textProperty().bind(character.getBattleAttributes().getHealthProperty().asString());
		goldLabel.textProperty().bind(character.getGoldProperty().asString());
		expLabel.textProperty().bind(character.getExpProperty().asString());
		cycleLabel.textProperty().bind(world.getCycleProperty().asString());
        alliedSoldierLabel.textProperty().bind(character.getSoldierCountProperty().asString());

		shopBuyMenu = new VBox();
		shopSellMenu = new VBox();
		// Load buy menu only once.
		loadBuyMenu();
		shopMenu.setContent(shopBuyMenu);
	}

	public void loadBuyMenu() {
		for (Item item : shop.getBuyStock()) {
			loadShopItem(item, ShopTab.BUY);
		}
	}

	public void loadSellMenu() {
		List<Item> items = world.getInventoryManager().getInventory().getItems();
		for (Item item : items) {
			onLoadItem(item);
		}

		// Load each item as a sellable item in the shop.
		for (Item item : items) {
			loadShopItem(item, ShopTab.SELL);
		}
	}

	public void loadShopItem(Item item, ShopTab shopTab) {
		GridPane itemContainer = new GridPane();

		// Fill width of parent container.
		itemContainer.gridLinesVisibleProperty().set(true);
		
		// --- Column Constraints ---
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(60);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
		ColumnConstraints col3 = new ColumnConstraints();
        col3.setMinWidth(60);
		ColumnConstraints col4 = new ColumnConstraints();
		col4.setMinWidth(60);
		col4.setPrefWidth(60);
		col4.setMaxWidth(60);

        itemContainer.getColumnConstraints().addAll(col1, col2, col3, col4);

        // --- Row Constraints ---
        RowConstraints row1 = new RowConstraints(Control.USE_COMPUTED_SIZE);
		row1.setMinHeight(Control.USE_COMPUTED_SIZE);

		itemContainer.getRowConstraints().addAll(row1);

		// Set image view.
		ImageView view = new ImageView(item.getEntityImage());
		GridPane.setValignment(view, VPos.CENTER);
		GridPane.setHalignment(view, HPos.CENTER);

		// Set item description cell.
		VBox itemDescription = new VBox();
		Label itemName = new Label(item.getClass().getSimpleName());
		itemName.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));

		Label description = new Label(item.getDescription());
		description.setWrapText(true);
		description.setMinHeight(Control.USE_PREF_SIZE);

		itemDescription.getChildren().addAll(itemName, description);
		itemDescription.setPadding(new Insets(4, 8, 4, 8));

		Label priceLabel = new Label("$" + item.getValue());
		GridPane.setValignment(priceLabel, VPos.CENTER);
		GridPane.setHalignment(priceLabel, HPos.CENTER);

		Button shopButton = new Button();
		shopButton.setMaxWidth(Double.MAX_VALUE);
		shopButton.setMaxHeight(Double.MAX_VALUE);

		if (shopTab == ShopTab.BUY) {
			shopButton.setText("Buy");
			// Attach click handler.
			shopButton.setOnAction((e) -> {
				Item boughtItem = shop.buyItem(item);

				// Load it as a sellable item in the sell menu.
				loadShopItem(boughtItem, ShopTab.SELL);

				// Load the ui element for the item in the unequipped inventory for both the shop UI
				// and the game UI.
				onLoadItem(boughtItem);
				gameController.onLoadItem(boughtItem);

			});
		} else {
			shopButton.setText("Sell");
			// Attach click handler.
			shopButton.setOnAction((e) -> {
				shop.sellItem(item);
			});
		}

		itemContainer.add(view, 0, 0);
		itemContainer.add(itemDescription, 1, 0);
		itemContainer.add(priceLabel, 2, 0);
		itemContainer.add(shopButton, 3, 0);

		trackExistence(item, itemContainer);

		if (shopTab == ShopTab.BUY) {
			shopBuyMenu.getChildren().add(itemContainer);
		} else {
			shopSellMenu.getChildren().add(itemContainer);
		}
	}

	public void clearSellMenuInShop() {
		shopSellMenu.getChildren().clear();

		// Clear the unequipped inventory views.
		for (ImageView itemView : sellItemViews) {
			unequippedInventory.getChildren().remove(itemView);
		}
		// Clear the ImageView sellable items list.
		sellItemViews.clear();
	}

	@FXML
    private void handleBuyTab() throws IOException {
		shopMenu.setContent(shopBuyMenu);
    }

	@FXML
    private void handleSellTab() throws IOException {
		shopMenu.setContent(shopSellMenu);
    }

	@FXML
    private void exitShop() throws IOException {
		clearSellMenuInShop();
		switchToGame();
		gameController.resume();
    }


	// ==================================================================================
	// Pair backendmodel entities with frontend entities.
	// ==================================================================================

	/**
	 * Pair a inventory item with a corresponding ImageView which is loaded in the
	 * unequipped inventory.
	 * @param item
	 */
	private void onLoadItem(Item item) {
		if (item == null) {
			return;
		}
		ImageView view = new ImageView(item.getEntityImage());
		GridPane.setColumnIndex(view, item.getX());
		GridPane.setRowIndex(view, item.getY());
		sellItemViews.add(view);
		trackExistence(item, view);
		unequippedInventory.getChildren().add(view);
	}


	private void trackExistence(Item item, Node node) {
		item.shouldExist().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
				if (newValue == false) {
					shopSellMenu.getChildren().remove(node);
					unequippedInventory.getChildren().remove(node);
				}
            }
        });
	}

	// ==================================================================================
	// Menu Switchers.
	// ==================================================================================

	public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    private void switchToGame() {
        gameSwitcher.switchMenu();
    }

}
