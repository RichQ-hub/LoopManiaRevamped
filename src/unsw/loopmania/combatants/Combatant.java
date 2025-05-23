package unsw.loopmania.combatants;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.effects.Effect;
import unsw.loopmania.entity.MovingEntity;

public abstract class Combatant extends MovingEntity {
	private DoubleProperty health;

	private DoubleProperty maxHealth;
	private List<Effect> effects;
	
	public Combatant(PathPosition position, double maxHealth) {
		super(position);
		this.health = new SimpleDoubleProperty(maxHealth);
		this.maxHealth = new SimpleDoubleProperty(maxHealth);
		this.effects = new ArrayList<>();
	}

	// ==================================================================================
	// Property Getters.
	// ==================================================================================

	public DoubleProperty getHealthProperty() {
		return health;
	}

	public DoubleProperty getMaxHealthProperty() {
		return maxHealth;
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

	public double getHealth() {
		return health.get();
	}

	public void setHealth(double health) {
		this.health.set(health);
	}

	public double getMaxHealth() {
		return maxHealth.get();
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth.set(maxHealth);
	}

}
