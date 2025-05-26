package unsw.loopmania.battle.loot;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.cards.Card;
import unsw.loopmania.items.Item;

public class Loot {
	private List<Item> items;
	private List<Card> cards;
	private int gold;
	private int exp;

	public Loot(int gold, int exp) {
		this.gold = gold;
		this.exp = exp;
		this.items = new ArrayList<>();
		this.cards = new ArrayList<>();
	}

	public void addItemReward(Item item) {
		items.add(item);
	}

	public void addCardReward(Card card) {
		cards.add(card);
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

}
