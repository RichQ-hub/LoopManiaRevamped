package unsw.loopmania.battle.effects;

import java.util.ListIterator;

import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public abstract class Effect {
	private Battleable target;
	private int uses; // -1 Represents a permanent effect.
	private EffectTrigger trigger; 

	public enum EffectTrigger {
		ON_MOVE,
		ON_HIT,
		ON_ATTACK,
	}

	public Effect(int uses, EffectTrigger trigger) {
		this.uses = uses;
		this.trigger = trigger;
	}

	public abstract void useEffect(ListIterator<Effect> activeEffectsIterator);
	public abstract Effect copyEffect();
	public abstract void acceptModifier(EffectModifier modifier);
	public abstract void printInfo();

	public void activate(ListIterator<Effect> activeEffectsIterator) {
		useEffect(activeEffectsIterator);
		decrementUses();
	}

	public void decrementUses() {
		if (uses > 0) {
			this.uses--;
		}
	}

	public boolean isActive() {
		return uses > 0 || uses == -1;
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public Battleable getTarget() {
		return target;
	}

	public void setTarget(Battleable target) {
		this.target = target;
	}

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public EffectTrigger getTrigger() {
		return trigger;
	}

	public void setTrigger(EffectTrigger trigger) {
		this.trigger = trigger;
	}
}
