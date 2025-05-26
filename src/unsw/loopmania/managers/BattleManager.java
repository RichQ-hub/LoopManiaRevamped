package unsw.loopmania.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.effects.Effect;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.combatants.Enemy;

public class BattleManager {
	private LoopManiaWorld world;
	private Character character;
	private List<Battleable> enemies;

	public BattleManager(LoopManiaWorld world, Character character) {
		this.world = world;
		this.character = character;
		this.enemies = new ArrayList<>();
	}

	/**
     * Run the expected battles in the world, based on current world state.
     * @return list of enemies which have been killed
     */
	public List<Battleable> battle() {
		for (Battleable enemy : enemies) {
			if (enemy.isWithinBattleRadius(character)) {
				List<Battleable> supportEnemies = getSupportEnemies(enemy);

				List<Battleable> battleEnemies = new ArrayList<>();
				battleEnemies.addAll(supportEnemies);
				battleEnemies.add(enemy);

				return runBattleNew(battleEnemies);
			}
		}
		return null;
	}

	public List<Battleable> getSupportEnemies(Battleable triggerEnemy) {
		List<Battleable> supports = new ArrayList<>();
		for (Battleable enemy : enemies) {
			if (triggerEnemy != enemy && enemy.isWithinSupportRadius(character)) {
				supports.add(enemy);
			}
		}
		return supports;
	}

	/**
     * Battle order: Entities attack their opponent in the order they are added to the world
     */
    public List<Battleable> runBattleNew(List<Battleable> battleEnemies) {

		List<Battleable> deadEnemies = new ArrayList<>();

		List<Battleable> battleEntities = new ArrayList<>();
		battleEntities.add(character);
		battleEntities.addAll(battleEnemies);

		// While character is not defeated and there is enemy state entity in the battle
        while (character.isAlive() && !battleEnemies.isEmpty()) {
            for (Battleable e : battleEntities) {
				// If the current combatant is not alive (which can happen in a battle), then it can't attack
				// so we move on to the next combatant.
				if (!e.isAlive()) {
					continue;
				}

				System.out.println("============================================");
				System.out.println(e.getClass().getSimpleName().toUpperCase() + "\n");

                // For each entity, attack all opponents.
                List<Battleable> entitiesToAttack = e.getEntitiesToAttack(battleEntities);

				System.out.println("Attack Effects:");
				for (Effect ae : e.getBattleAttributes().getAttackEffects()) {
					ae.printInfo();
				}

				System.out.println("\nOpponents:");
				for (Battleable o : entitiesToAttack) {
					System.out.println(" - " + o.getClass().getSimpleName());
				}

				System.out.println("\n--------------------------------------------");

                for (Battleable opponent : entitiesToAttack) {
					// Can only attack opponent if they are alive.
					if (opponent.isAlive()) {
						System.out.println("\nAttacking -- {" + opponent.getClass().getSimpleName() + "}: {" + opponent.getBattleAttributes().getHealth() + "}");
						e.attack(opponent);

						// Log info.
						System.out.println("  ************************");
						opponent.printInfo();
					}

					// If opponent dies, add to the list of defeated enemies.
					if (!opponent.isAlive()) {
						if (battleEnemies.remove(opponent)) {
							// If we were able to remove the opponent, that means it was an enemy
							// so we add it to the list of dead enemies.
							deadEnemies.add(opponent);
							killEnemy(opponent);
						}
					}
				}

				System.out.println();
            }

            // After every entity has their turn, note down the defeated entities
            List<Battleable> deadEntities = battleEntities.stream()
                .filter(e -> !e.isAlive())
                .collect(Collectors.toList());

            // Remove them from the entities that can be in the battle
            battleEntities.removeAll(deadEntities);
        }

		return deadEnemies;
    }

	/**
     * Kill an enemy.
     * @param enemy enemy to be killed
     */
    private void killEnemy(Battleable enemy) {
        enemy.destroy();
        enemies.remove(enemy);
    }

	public void moveEnemies() {
		for (Battleable e : enemies) {
			e.move();
		}
	}

	/**
	 * Double dispatch method.
	 * @param entity
	 */
	public void addBattleableEntity(Battleable entity) {
		entity.addToBattleManager(this);
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	// ==================================================================================
	// Getters and Setters.
	// ==================================================================================

	public LoopManiaWorld getWorld() {
		return world;
	}

	public void setWorld(LoopManiaWorld world) {
		this.world = world;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public List<Battleable> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Battleable> enemies) {
		this.enemies = enemies;
	}

}
