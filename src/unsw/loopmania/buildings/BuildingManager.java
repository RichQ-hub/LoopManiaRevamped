package unsw.loopmania.buildings;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.cards.Card;
import unsw.loopmania.spawners.Spawner;

public class BuildingManager {
	private LoopManiaWorld world;
	private List<Card> cards;
	private List<Building> buildings;
	private List<Spawner> spawners;

	public BuildingManager(LoopManiaWorld world) {
		this.world = world;
		this.cards = new ArrayList<>();
		this.buildings = new ArrayList<>();
		this.spawners = new ArrayList<>();
	}

	 /**
     * Remove the card from the world, and spawn and return a building instead where the card was dropped.
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    public Building convertCardToBuildingByCoordinates(int cardX, int cardY, int buildingX, int buildingY) {
        // Get the card.
        Card card = null;
        for (Card c: cards) {
            if ((c.getX() == cardX) && (c.getY() == cardY)) {
                card = c;
                break;
            }
        }

		// If we building location is not valid for the current card, return null.
		if (!card.isValidDropLocation(world, buildingX, buildingY)) {
			return null;
		}
        
        // Now spawn building
        Building newBuilding = card.createBuilding(world, buildingX, buildingY);
        addBuilding(newBuilding);

        // Destroy the card
        card.destroy();
        cards.remove(card);
        shiftCardsDownFromXCoordinate(cardX);

        return newBuilding;
    }

	/**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x) {
        for (Card c: cards){
            if (c.getX() >= x) {
                c.x().set(c.getX() - 1);
            }
        }
    }

	public Building getBuildingByCoordinates(int x, int y) {
		for (Building b : buildings) {
			if (b.getX() == x && b.getY() == y) {
				return b;
			}
		}
		return null;
	}

	public void addBuildingSpawner(Building building) {
		building.addToBuildingManager(this);
	}

	public void addBuilding(Building building) {
		buildings.add(building);
	}

	public void addCard(Card card) {
		// Remove the oldest card (first card) if the cards list is full.
		if (cards.size() >= world.getMapWidth()) {
			cards.remove(0);
		}

		// Ensure that its x value is the last in the list.
		card.x().setValue(cards.size());
		cards.add(card);
	}

	public void addSpawner(Spawner spawner) {
		spawners.add(spawner);
	}
}
