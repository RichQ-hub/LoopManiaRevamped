package unsw.loopmania.battle;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class Attack {
	private List<Effect> effects;
	private List<EffectModifier> effectModifiers;

	public Attack() {
		this.effects = new ArrayList<>();
		this.effectModifiers = new ArrayList<>();
	}

	public void applyModifier(EffectModifier modifier) {
		for (Effect e : effects) {
			e.acceptModifier(modifier);
		}
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public void addEffectModifier(EffectModifier modifier) {
		effectModifiers.add(modifier);
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

	public List<EffectModifier> getEffectModifiers() {
		return effectModifiers;
	}

	public void setEffectModifiers(List<EffectModifier> effectModifiers) {
		this.effectModifiers = effectModifiers;
	}

}
