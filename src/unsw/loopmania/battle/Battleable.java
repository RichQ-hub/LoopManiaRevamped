package unsw.loopmania.battle;

import java.util.List;

import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.combatants.Character;

public interface Battleable {
	public void attack(Battleable combatant);
	public void takeAttack(Attack attack);
	public boolean isEnemy();
	public boolean isAlive();
	public boolean isWithinBattleRadius(Character character);
	public boolean isWithinSupportRadius(Character character);
	public void destroy();
	public void move();
	public Loot dropLoot();
	public void printInfo();

	public List<Battleable> getEntitiesToAttack(List<Battleable> battleEntities);

	public void addToBattleManager(BattleManager manager);

	public BattleAttributes getBattleAttributes();
}
