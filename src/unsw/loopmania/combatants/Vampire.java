package unsw.loopmania.combatants;

import java.util.Random;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleManager;

public class Vampire extends Combatant {

	public Vampire(PathPosition position) {
		super(position, 30);
		super.setEntityImageByPath("src/images/vampire.png");
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		manager.addEnemy(this);;
	}

	@Override
	public void attack(Combatant opponent) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'attack'");
	}

	@Override
	public void takeDamage(Attack attack) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
	}

	@Override
	public void move() {
		int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        } else if (directionChoice == 1){
            moveDownPath();
        }
	}
	
}
