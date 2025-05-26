package unsw.loopmania.battle.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unsw.loopmania.cards.Card;
import unsw.loopmania.items.Item;

public class LootTable {
	private List<LootItem> lootItems;
	private List<LootCard> lootCards;
	private int goldReward;
	private int expReward;
	private final Random random = new Random();

	public LootTable(int goldReward, int expReward) {
		this.goldReward = goldReward;
		this.expReward = expReward;
		this.lootItems = new ArrayList<>();
		this.lootCards = new ArrayList<>();
	}

	public void addLootItem(LootItem item) {
		lootItems.add(item);
	}

	public void addLootCard(LootCard card) {
		lootCards.add(card);
	}

	public Loot dropLoot() {
		Loot loot = new Loot(goldReward, expReward);

		Item newItem = dropRandomItem();
		if (newItem != null) {
			loot.addItemReward(newItem);
		}

		Card newCard = dropRandomCard();
		if (newCard != null) {
			loot.addCardReward(newCard);
		}

		return loot;
	}

	public Item dropRandomItem() {
		double totalWeight = lootItems.stream().mapToDouble(LootItem::getDropChance).sum(); // Could change this to 100%
		double randomValue = random.nextDouble() * totalWeight;

		double cumulativeWeight = 0;

		for (LootItem i : lootItems) {
			cumulativeWeight += i.getDropChance();
			if (randomValue <= cumulativeWeight) {
				return i.dropItem();
			}
		}

		return null;
	}

	public Card dropRandomCard() {
		double totalWeight = lootCards.stream().mapToDouble(LootCard::getDropChance).sum();
		double randomValue = random.nextDouble() * totalWeight;

		double cumulativeWeight = 0;

		for (LootCard c : lootCards) {
			cumulativeWeight += c.getDropChance();
			if (randomValue <= cumulativeWeight) {
				return c.dropCard();
			}
		}

		return null;
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public List<LootItem> getLootItems() {
		return lootItems;
	}

	public void setLootItems(List<LootItem> lootItems) {
		this.lootItems = lootItems;
	}

	public List<LootCard> getLootCards() {
		return lootCards;
	}

	public void setLootCards(List<LootCard> lootCards) {
		this.lootCards = lootCards;
	}

	public int getGoldReward() {
		return goldReward;
	}

	public void setGoldReward(int goldReward) {
		this.goldReward = goldReward;
	}

	public int getExpReward() {
		return expReward;
	}

	public void setExpReward(int expReward) {
		this.expReward = expReward;
	}

}

/**
 * HOW IT WORKS:
 * 
 * 	You can imagine all the items laid out on a number line, with each item occupying a segment based on its weight:
	0       50      80      95      100
	|--------|-------|------|--------|
	| Health | Mana  | Sword|Legendary
	| Potion |Potion |Flames|Armor
	So:

	Health Potion spans 0–50 (50%)
	Mana Potion spans 50–80 (30%)
	Sword of Flames spans 80–95 (15%)
	Legendary Dragon Armor spans 95–100 (5%)

	Now when a random number (e.g., 72) is generated:

	It's in the range of Mana Potion → it gets selected.
 */
