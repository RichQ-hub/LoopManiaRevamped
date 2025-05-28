package unsw.loopmania.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.entity.Entity;

public class BattleAttributes {
	private Entity combatant;
	private DoubleProperty health;
	private double maxHealth;

	private double battleRadius;
	private double supportRadius;

	private BattleState battleState;
	private List<Effect> activeEffects;

	// Battle Effects.
	private List<Effect> attackEffects;
	private List<EffectModifier> defenseModifiers;
	private List<EffectModifier> attackModifiers;

	public BattleAttributes(Entity combatant, double maxHealth, double battleRadius, double supportRadius, BattleState battleState) {
		this.combatant = combatant;
		this.maxHealth = maxHealth;
		this.health = new SimpleDoubleProperty(maxHealth);
		this.battleRadius = battleRadius;
		this.supportRadius = supportRadius;
		this.battleState = battleState;
		this.activeEffects = new ArrayList<>();
		this.attackEffects = new ArrayList<>();
		this.defenseModifiers = new ArrayList<>();
		this.attackModifiers = new ArrayList<>();
	}

	public void applyModifiers(List<EffectModifier> modifiers) {
		for (EffectModifier m : modifiers) {
			for (Effect e : activeEffects) {
				e.acceptModifier(m);
			}
		}
	}

	/**
	 * Activate effects via some trigger. Uses an iterator so that inserting new effects
	 * into the list is valid whilst iterating over it. (Some effects insert new effects
	 * into the list when activated).
	 * @param trigger
	 */
	public void triggerEffects(Effect.EffectTrigger trigger) {
		// Generate a new list iterator so that it resets to the beginning of the list.
		ListIterator<Effect> activeEffectsIterator = activeEffects.listIterator();

		while (activeEffectsIterator.hasNext()) {
			Effect e = activeEffectsIterator.next();
			if (e.getTrigger() == trigger) {
				e.activate(activeEffectsIterator);
			}
		}

		cleanseActiveEffects();
	}

	/**
	 * Remove any inactive effects.
	 */
	public void cleanseActiveEffects() {
		this.activeEffects = activeEffects.stream().filter(effect -> effect.getUses() > 0).collect(Collectors.toList());
	}

	public boolean isWithinBattleRadius(Character character) {
		return Math.pow((combatant.getX() - character.getX()), 2) + Math.pow((combatant.getY() - character.getY()), 2) <= battleRadius;
	}

	public boolean isWithinSupportRadius(Character character) {
		return Math.pow((combatant.getX() - character.getX()), 2) + Math.pow((combatant.getY() - character.getY()), 2) <= supportRadius;
	}

	// ==================================================================================
	// Attack Object Methods.
	// ==================================================================================

	/**
	 * Applies all equipment modifiers onto the incoming attack.
	 * @param attack
	 */
	public void modifyOutgoingAttack(Attack attack) {
		for (EffectModifier m : attackModifiers) {
			attack.applyModifier(m);
		}
	}

	/**
	 * Applies all equipment modifiers onto the incoming attack.
	 * @param attack
	 */
	public void modifyIncomingAttack(Attack attack) {
		for (EffectModifier m : defenseModifiers) {
			attack.applyModifier(m);
		}
	}

	// ==================================================================================
	// Append Methods.
	// ==================================================================================

	/**
	 * Adds an effect into the list of active effects.
	 * @param effect
	 */
	public void removeActiveEffect(Effect effect) {
		activeEffects.remove(effect);
	}

	public void removeAttackEffect(Effect effect) {
		attackEffects.remove(effect);
	}

	public void removeDefenseModifier(EffectModifier modifier) {
		defenseModifiers.remove(modifier);
	}

	public void removeAttackModifier(EffectModifier modifier) {
		attackModifiers.remove(modifier);
	}

	// ==================================================================================
	// Remove Methods.
	// ==================================================================================

	/**
	 * Adds an effect into the list of active effects.
	 * @param effect
	 */
	public void addActiveEffect(Effect effect) {
		boolean hasEffect = activeEffects.stream().anyMatch(e -> e.getClass().equals(effect.getClass()));
		if (!hasEffect) {
			activeEffects.add(effect);
		}
	}

	public void addAttackEffect(Effect effect) {
		boolean hasEffect = attackEffects.stream().anyMatch(e -> e.getClass().equals(effect.getClass()));
		if (!hasEffect) {
			attackEffects.add(effect);
		}
	}

	public void addDefenseModifier(EffectModifier modifier) {
		boolean hasEffect = defenseModifiers.stream().anyMatch(m -> m.getClass().equals(m.getClass()));
		if (!hasEffect) {
			defenseModifiers.add(modifier);
		}
	}

	public void addAttackModifier(EffectModifier modifier) {
		boolean hasEffect = attackModifiers.stream().anyMatch(m -> m.getClass().equals(m.getClass()));
		if (!hasEffect) {
			attackModifiers.add(modifier);
		}
	}

	// ==================================================================================
	// Property Getters.
	// ==================================================================================

	public DoubleProperty getHealthProperty() {
        return health;
    }

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public double getHealth() {
		return health.get();
	}

	public void setHealth(double health) {
		this.health.set(health);
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getBattleRadius() {
		return battleRadius;
	}

	public void setBattleRadius(double battleRadius) {
		this.battleRadius = battleRadius;
	}

	public double getSupportRadius() {
		return supportRadius;
	}

	public void setSupportRadius(double supportRadius) {
		this.supportRadius = supportRadius;
	}

	public BattleState getBattleState() {
		return battleState;
	}

	public void setBattleState(BattleState battleState) {
		this.battleState = battleState;
	}

	public List<Effect> getActiveEffects() {
		return activeEffects;
	}

	public void setActiveEffects(List<Effect> activeEffects) {
		this.activeEffects = activeEffects;
	}

	public List<Effect> getAttackEffects() {
		return attackEffects;
	}

	public void setAttackEffects(List<Effect> attackEffects) {
		this.attackEffects = attackEffects;
	}

	public List<EffectModifier> getDefenseModifiers() {
		return defenseModifiers;
	}

	public void setDefenseModifiers(List<EffectModifier> defenseModifiers) {
		this.defenseModifiers = defenseModifiers;
	}

	public Entity getCombatant() {
		return combatant;
	}

	public void setCombatant(Entity combatant) {
		this.combatant = combatant;
	}

	public List<EffectModifier> getAttackModifiers() {
		return attackModifiers;
	}

	public void setAttackModifiers(List<EffectModifier> attackModifiers) {
		this.attackModifiers = attackModifiers;
	}
}
