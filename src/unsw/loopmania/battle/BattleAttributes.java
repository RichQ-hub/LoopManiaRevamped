package unsw.loopmania.battle;

import java.util.ArrayList;
import java.util.List;
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
	private List<Effect> baseAttackEffects;
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
		this.baseAttackEffects = new ArrayList<>();
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
	 * Trigger any effects that match the trigger.
	 * @param trigger
	 */
	public void triggerEffects(Effect.EffectTrigger trigger) {
		for (Effect e : activeEffects) {
			if (e.getTrigger() == trigger) {
				e.activate();
			}
		}

		cleanseActiveEffects();
	}

	/**
	 * Remove any inactive effects.
	 */
	public void cleanseActiveEffects() {
		this.activeEffects = activeEffects.stream().filter(effect -> effect.isActive()).collect(Collectors.toList());
	}

	public boolean isWithinBattleRadius(Character character) {
		return Math.pow((combatant.getX() - character.getX()), 2) + Math.pow((combatant.getY() - character.getY()), 2) <= battleRadius;
	}

	public boolean isWithinSupportRadius(Character character) {
		return Math.pow((combatant.getX() - character.getX()), 2) + Math.pow((combatant.getY() - character.getY()), 2) <= supportRadius;
	}

	// ==================================================================================
	// Print Info.
	// ==================================================================================

	public void printBattleAttributesInfo() {
		// Print Active Effects.
		System.out.println("  HEALTH: " + getHealth());

		System.out.println("  BATTLE STATE: " + getBattleState().getClass().getSimpleName());

		// Print Active Effects.
		System.out.println("  ACTIVE EFFECTS: {");
		for (Effect e : activeEffects) {
			e.printInfo();
		}
		System.out.println("  }");

		// Print Defense Modifiers.
		System.out.println("  DEFENSE MODIFIERS: {");
		for (EffectModifier m : defenseModifiers) {
			m.printInfo();
		}
		System.out.println("  }");

		// Print Attack Modifiers.
		System.out.println("  ATTACK MODIFIERS: {");
		for (EffectModifier m : attackModifiers) {
			m.printInfo();
		}
		System.out.println("  }");
	}

	// ==================================================================================
	// Attack Object Methods.
	// ==================================================================================

	public void insertBaseAttackEffects(Attack attack) {
		for (Effect baseEffect : baseAttackEffects) {
			Effect copy = baseEffect.copyEffect();
			attack.addEffect(copy);
		}
	}

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
	// Remove Methods.
	// ==================================================================================

	/**
	 * Adds an effect into the list of active effects.
	 * @param effect
	 */
	public void removeActiveEffect(Effect effect) {
		activeEffects.remove(effect);
	}

	public void removeBaseAttackEffect(Effect effect) {
		baseAttackEffects.remove(effect);
	}

	public void removeDefenseModifier(EffectModifier modifier) {
		defenseModifiers.remove(modifier);
	}

	public void removeAttackModifier(EffectModifier modifier) {
		attackModifiers.remove(modifier);
	}

	// ==================================================================================
	// Append Methods.
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

	public void addBaseAttackEffect(Effect effect) {
		boolean hasEffect = baseAttackEffects.stream().anyMatch(e -> e.getClass().equals(effect.getClass()));
		if (!hasEffect) {
			baseAttackEffects.add(effect);
		}
	}

	public void addDefenseModifier(EffectModifier modifier) {
		boolean hasEffect = defenseModifiers.stream().anyMatch(m -> m.getClass().equals(modifier.getClass()));
		if (!hasEffect) {
			defenseModifiers.add(modifier);
		}
	}

	public void addAttackModifier(EffectModifier modifier) {
		boolean hasEffect = attackModifiers.stream().anyMatch(m -> m.getClass().equals(modifier.getClass()));
		if (!hasEffect) {
			attackModifiers.add(modifier);
		}
	}

	// TODO: Could replace the above with this function.
	public <T> boolean effectExists(T effect, List<T> effectList) {
		return effectList.stream().anyMatch(e -> e.getClass().equals(effect.getClass()));
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

	public List<Effect> getBaseAttackEffects() {
		return baseAttackEffects;
	}

	public void setBaseAttackEffects(List<Effect> attackEffects) {
		this.baseAttackEffects = attackEffects;
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
