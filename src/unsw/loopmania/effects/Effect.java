package unsw.loopmania.effects;

import unsw.loopmania.battle.Battleable;
import unsw.loopmania.effects.modifiers.EffectModifier;

public abstract class Effect {
	private Battleable target;
	private int uses;
	private EffectTrigger trigger; 

	public enum EffectTrigger {
		ON_MOVE,
		ON_HIT,
		ON_ATTACK,
	}

	public Effect(Battleable target, int uses, EffectTrigger trigger) {
		this.target = target;
		this.uses = uses;
		this.trigger = trigger;
	}

	public abstract void useEffect();
	public abstract void acceptModifier(EffectModifier modifier);

	public void activate() {
		useEffect();
		decrementUses();
	}

	public void decrementUses() {
		this.uses--;
	}

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
