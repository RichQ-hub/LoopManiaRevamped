package unsw.loopmania.combatants;

import java.util.Random;

import unsw.loopmania.PathPosition;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.managers.BattleManager;

public class Slug extends Enemy {

	public Slug(PathPosition position) {
		super(position, 20);
		super.setEntityImageByPath("src/images/slug.png");
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		manager.addEnemy(this);
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
