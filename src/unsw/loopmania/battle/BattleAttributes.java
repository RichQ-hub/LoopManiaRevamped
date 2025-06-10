package unsw.loopmania.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.battle.effects.Effect.EffectTrigger;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;
import unsw.loopmania.combatants.Character;

public class BattleAttributes {
	private Battleable combatant;
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

	public BattleAttributes(Battleable combatant, double maxHealth, double battleRadius, double supportRadius, BattleState battleState) {
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

	// ==================================================================================
	// Health Methods.
	// ==================================================================================

	// TODO: Incorporate these methods in effects.

	public void addHealth(double amount) {
		double newHealth = getHealth() + amount;
		if (newHealth > maxHealth) {
			newHealth = maxHealth;
		}
		setHealth(newHealth);
	}

	public void reduceHealth(double amount) {
		setHealth(getHealth() - amount);
	}

	// ==================================================================================
	// Battle Methods.
	// ==================================================================================

	public void attackOpponents(List<Battleable> battleEntities) {
		// Get opponents that are alive.
		List<Battleable> opponents = combatant.getEntitiesToAttack(battleEntities);
		for (Battleable opp : opponents) {
			System.out.println(String.format("\nAttacking -- {%s}: {%f}", opp.getClass().getSimpleName(), opp.getBattleAttributes().getHealth()));
			// Build attack.
			Attack attack = combatant.buildAttack();

			// Print Outgoing attack.
			attack.printInfo("Outgoing Attack");

			// Let the opponent take the attack.
			opp.takeAttack(attack);

			// Trigger on-attack effects.

			// TODO: Could move this outside of this for loop. For example we run into problems when we are a tranced
			// enemy that lasts only 2 attacks, but the getEntitiesToAttack() returns 3 enemies. The trance effect should
			// end by the 2nd enemy, but we continue attacking the 3rd enemy even though we reverted back to EnemyState
			// since the trance ended. This is becase we still continue to the 3rd enemy dur to this for loop.
			triggerEffects(EffectTrigger.ON_ATTACK);

			// Log info.
			opp.printInfo();
		}
	}

	public Attack buildAttack() {
		Attack attack = new Attack();
		
		insertBaseAttackEffects(attack);

		// DEBUG: Print attack.
		attack.printInfo("Base Attack");

		// Apply attack modifiers (buffs) the character might have.
		modifyOutgoingAttack(attack);

		return attack;
	}

	public void takeAttack(Attack attack) {
		// Modify any incoming effects by the combatant's pre-existing defense modifiers.
		modifyIncomingAttack(attack);

		// DEBUG: Print attack.
		attack.printInfo("Incoming Attack");

		// Add all offensive effects onto the person.
		for (Effect e : attack.getEffects()) {
			e.setTarget(combatant);

			// Run initial setup code when the effect is added.
			e.setupEffect();

			// Add the effect to the list of active effects.
			addActiveEffect(e);
		}

		// Trigger on-hit effects.
		triggerEffects(Effect.EffectTrigger.ON_HIT);

		// Trigger death effects if health drops below 0.
		if (!combatant.isAlive()) {
			triggerEffects(Effect.EffectTrigger.ON_DEATH);
		}
	}

	public boolean isWithinBattleRadius(Character character) {
		return Math.pow((combatant.getX() - character.getX()), 2) + Math.pow((combatant.getY() - character.getY()), 2) <= battleRadius;
	}

	public boolean isWithinSupportRadius(Character character) {
		return Math.pow((combatant.getX() - character.getX()), 2) + Math.pow((combatant.getY() - character.getY()), 2) <= supportRadius;
	}

	// ==================================================================================
	// Effect Methods.
	// ==================================================================================

	/**
	 * Trigger any effects that match the trigger.
	 * @param trigger
	 */
	public void triggerEffects(Effect.EffectTrigger trigger) {
		for (Effect e : activeEffects) {
			if (e.getTrigger() == trigger && e.isActive()) {
				e.activate();
			}
		}

		cleanseActiveEffects();
	}

	public void applyModifiers(List<EffectModifier> modifiers) {
		for (EffectModifier m : modifiers) {
			for (Effect e : activeEffects) {
				e.acceptModifier(m);
			}
		}
	}

	/**
	 * Remove any inactive effects.
	 */
	public void cleanseActiveEffects() {
		this.activeEffects = activeEffects.stream().filter(effect -> effect.isActive()).collect(Collectors.toList());
	}

	// ==================================================================================
	// Attack Object Methods.
	// ==================================================================================

	/**
	 * Inserts base attack effects present in the current combatant.
	 * @param attack
	 */
	public void insertBaseAttackEffects(Attack attack) {
		for (Effect baseEffect : baseAttackEffects) {
			Effect copy = baseEffect.copyEffect();
			attack.addEffect(copy);
		}
	}

	/**
	 * Applies all attack modifiers onto the outgoing attack.
	 * @param attack
	 */
	public void modifyOutgoingAttack(Attack attack) {
		for (EffectModifier m : attackModifiers) {
			attack.applyModifier(m);
		}
	}

	/**
	 * Applies all defensive modifiers onto the incoming attack.
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

	public Battleable getCombatant() {
		return combatant;
	}

	public void setCombatant(Battleable combatant) {
		this.combatant = combatant;
	}

	public List<EffectModifier> getAttackModifiers() {
		return attackModifiers;
	}

	public void setAttackModifiers(List<EffectModifier> attackModifiers) {
		this.attackModifiers = attackModifiers;
	}
}
