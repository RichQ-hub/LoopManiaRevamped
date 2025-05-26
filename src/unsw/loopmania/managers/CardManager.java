package unsw.loopmania.managers;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.cards.Card;

public class CardManager {
	private LoopManiaWorld world;
	private List<Card> cards;

	public CardManager(LoopManiaWorld world) {
		this.world = world;
		this.cards = new ArrayList<>();
	}

	public Card addCard(Card card) {
		// Remove the oldest card (first card) if the cards list is full.
		if (cards.size() >= world.getMapWidth()) {
			destroyCardByIndex(0);
		}

		// Ensure that its x value is the last in the list.
		card.x().setValue(cards.size());
		cards.add(card);
		return card;
	}

	/**
     * Remove card at a particular index of cards (position in gridpane of unplayed cards).
     * @param index the index of the card, from 0 to length-1
     */
    public void destroyCardByIndex(int index) {
        Card c = cards.get(index);
        int x = c.getX();
        c.destroy();
        cards.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

	/**
     * Shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    public void shiftCardsDownFromXCoordinate(int x) {
        for (Card c: cards){
            if (c.getX() >= x) {
                c.x().set(c.getX() - 1);
            }
        }
    }

	public Card getCardByCoordinates(int x, int y) {
		for (Card c : cards) {
			if (c.getX() == x && c.getY() == y) {
				return c;
			}
		}
		return null;
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}
