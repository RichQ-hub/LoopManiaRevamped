package unsw.loopmania.battle;

import java.util.List;

import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.combatants.Character;

public interface Battleable {
	/**
	 * Attacks any given opponent in the current round. Only entities in the current battle
	 * ROUND are attackable, but it is read-only. However, changes to the battleEntities list
	 * (e.g. enemy additions during a battle) is only reflected in the next round.
	 * @param battleEntities - All entities present in the battle (modifiable).
	 * @param battleEntitiesInRound - Entities present in the current round (read-only).
	 */
	public void attackOpponents(List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound);

	/**
	 * Builds the attack for each unique battle entity.
	 * @return
	 */
	public Attack buildAttack();

	/**
	 * Entities apply any given modifications to an attack they receive during a battle
	 * before it is applied directly to itself.
	 * @param attack
	 * @param battleEntities - All entities present in the battle (modifiable).
	 * @param battleEntitiesInRound - Entities present in the current round (read-only).
	 */
	public void takeAttack(Attack attack, List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound);

	/**
	 * Returns whether a combatant is an enemy or an ally.
	 * @return
	 */
	public boolean isEnemy();

	/**
	 * Is the combatant alive.
	 * @return
	 */
	public boolean isAlive();

	/**
	 * Checks if the character is within the battle radius of a combatant (e.g. a slime).
	 * @param character
	 * @return
	 */
	public boolean isWithinBattleRadius(Character character);

	/**
	 * Checks if the character is within the support battle radius of a combatant (e.g. a slime).
	 * When battles are initiated when the character walks into the battle radius of an enemy,
	 * other other enemies can support that enemy during a battle if the character is also within
	 * their support radius.
	 * @param character
	 * @return
	 */
	public boolean isWithinSupportRadius(Character character);

	/**
	 * Marks the current combatant as destroyed, removing its frontend UI view as well as
	 * its model in the backend world.
	 */
	public void destroy();

	/**
	 * Specifies the movement behaviour of the combatant on the game path.
	 */
	public void move();

	/**
	 * Specifies the loot dropped by the combatant upon defeat on the conclusion
	 * of the battle, which is received by the Character.
	 * @return
	 */
	public Loot dropLoot();

	/**
	 * Print the combat stats of the combatant (e.g. defense modifiers, attack modifiers and
	 * active effects).
	 */
	public void printInfo();

	/**
	 * Obtain opponent entities to attack in the current round.
	 * @param battleEntities - Entities in the current round (read-only).
	 * @return
	 */
	public List<Battleable> getEntitiesToAttack(List<Battleable> battleEntities);

	/**
	 * Specifies the specific lists that this combatant should be added to in the battle manager.
	 * @param manager
	 */
	public void addToBattleManager(BattleManager manager);

	/**
	 * Return all the current battle stats of this combatant.
	 * @return
	 */
	public BattleAttributes getBattleAttributes();

	
	/**
	 * Get x position.
	 * @return
	 */
	public int getX();

	/**
	 * Get y position.
	 * @return
	 */
	public int getY();
}
