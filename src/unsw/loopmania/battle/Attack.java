package unsw.loopmania.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

		cleanseEffects();
	}

	public void addEffect(Effect effect) {
		boolean hasEffect = effects.stream().anyMatch(e -> e.getClass().equals(effect.getClass()));
		if (!hasEffect) {
			effects.add(effect);
		}
	}

	public void addEffectModifier(EffectModifier modifier) {
		effectModifiers.add(modifier);
	}

	public void cleanseEffects() {
		this.effects = effects.stream().filter(effect -> effect.getUses() > 0).collect(Collectors.toList());
	}

	public void printInfo(String title) {
		System.out.println(String.format("  %s: {", title));

		for (Effect e : effects) {
			e.printInfo();
		}
		System.out.println("  }");
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

	public List<EffectModifier> getEffectModifiers() {
		return effectModifiers;
	}

	public void setEffectModifiers(List<EffectModifier> effectModifiers) {
		this.effectModifiers = effectModifiers;
	}

}
