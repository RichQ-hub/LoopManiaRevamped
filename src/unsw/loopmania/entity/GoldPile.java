package unsw.loopmania.entity;

import org.javatuples.Pair;

import unsw.loopmania.combatants.Character;
import unsw.loopmania.observers.LocationObserver;

public class GoldPile extends StaticEntity implements LocationObserver<Character> {
	private int amount;

	public GoldPile(Pair<Integer, Integer> position, int amount) {
		super(position);
		super.setEntityImageByPath("src/images/gold_pile.png");
		this.amount = amount;
	}

	@Override
	public void update(Character entity) {
		if ((entity.getX() == getX()) && (entity.getY() == getY())) {
			entity.setGold(entity.getGold() + amount);
			this.destroy();
		}
	}

	@Override
	public boolean shouldObserverExist() {
		return shouldExist().get();
	}
	
}
