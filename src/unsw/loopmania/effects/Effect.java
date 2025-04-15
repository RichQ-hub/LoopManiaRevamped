package unsw.loopmania.effects;

import unsw.loopmania.effects.modifiers.EffectModifier;

public abstract class Effect {
	private int uses;

	public Effect(int uses) {
		this.uses = uses;
	}

	public abstract void acceptModifier(EffectModifier modifier);

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}
}
