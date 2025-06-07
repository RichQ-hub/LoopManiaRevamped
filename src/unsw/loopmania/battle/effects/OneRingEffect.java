package unsw.loopmania.battle.effects;

import java.util.ListIterator;

import unsw.loopmania.battle.effects.modifiers.EffectModifier;

public class OneRingEffect extends Effect {

	public OneRingEffect() {
		super(-1, EffectTrigger.ON_HIT);
	}

	@Override
	public void useEffect(ListIterator<Effect> activeEffectsIterator) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'useEffect'");
	}

	@Override
	public Effect copyEffect() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'copyEffect'");
	}

	@Override
	public void acceptModifier(EffectModifier modifier) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'acceptModifier'");
	}

	@Override
	public void printInfo() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'printInfo'");
	}
	
}
