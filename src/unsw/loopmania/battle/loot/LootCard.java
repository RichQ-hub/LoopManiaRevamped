package unsw.loopmania.battle.loot;

import unsw.loopmania.cards.Card;

public class LootCard {
	private Card card;
	private double dropChance;

	/**
	 * 
	 * @param item - Card to spawn.
	 * @param dropChance - Between 0 - 100
	 */
	public LootCard(Card card, double dropChance) {
		this.card = card;
		this.dropChance = dropChance;
	}

	public Card dropCard() {
		return card.copyCard();
	}

	public double getDropChance() {
		return dropChance;
	}
}
