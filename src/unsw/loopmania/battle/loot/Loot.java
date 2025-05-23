package unsw.loopmania.battle.loot;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.cards.Card;
import unsw.loopmania.items.Item;

public class Loot {
	private List<Item> items;
	private List<Card> cards;

	public Loot() {
		this.items = new ArrayList<>();
		this.cards = new ArrayList<>();
	}

	public void addItemReward(Item item) {
		items.add(item);
	}

	public void addCardReward(Card card) {
		cards.add(card);
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Card> getCards() {
		return cards;
	}

}
