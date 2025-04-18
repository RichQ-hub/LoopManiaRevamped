package unsw.loopmania.combatants;

import unsw.loopmania.PathPosition;

/**
 * A basic form of enemy in the world.
 */
public abstract class Enemy extends Combatant {
	private int battleRadius;
    private int supportRadius;
	
    public Enemy(PathPosition position, double maxHealth) {
        super(position, maxHealth);
    }

	public int getBattleRadius() {
		return battleRadius;
	}

	public void setBattleRadius(int battleRadius) {
		this.battleRadius = battleRadius;
	}

	public int getSupportRadius() {
		return supportRadius;
	}

	public void setSupportRadius(int supportRadius) {
		this.supportRadius = supportRadius;
	}
}
