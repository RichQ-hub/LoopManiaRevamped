package unsw.loopmania.combatants;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.effects.Effect;
import unsw.loopmania.entity.Battleable;
import unsw.loopmania.entity.MovingEntity;

public abstract class Combatant extends MovingEntity implements Battleable {
	private double health;
	private List<Effect> effects;
	
	public Combatant(PathPosition position, double health) {
		super(position);
		this.health = health;
		this.effects = new ArrayList<>();
	}

	public abstract void attack(Combatant opponent);
	public abstract void takeDamage(Attack attack);
	public abstract void move();

	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	public List<Effect> getEffects() {
		return effects;
	}
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

}
