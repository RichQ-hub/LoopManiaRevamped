package unsw.loopmania.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.combatants.Enemy;

public class BattleManager {
	private LoopManiaWorld world;
	private Character character;
	private List<Enemy> enemies;

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

				return runBattle(battleEnemies);
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
    public List<Battleable> runBattle(List<Battleable> battleEnemies) {

		List<Battleable> deadEnemies = new ArrayList<>();

		List<Battleable> battleEntities = new ArrayList<>();
		battleEntities.add(character);
		battleEntities.addAll(character.getAlliedSoldiers());
		battleEntities.addAll(battleEnemies);

		// While character is not defeated and there are no more enemies in battle.
        while (character.isAlive() && battleEntities.stream().anyMatch(Battleable::isEnemy)) {
            for (Battleable e : battleEntities) {
				// If the current combatant is not alive (which can happen in a battle), then it can't attack
				// so we move on to the next combatant.
				if (!e.isAlive()) {
					continue;
				}

				// For each entity, attack all opponents.
                List<Battleable> entitiesToAttack = e.getEntitiesToAttack(battleEntities);
				if (entitiesToAttack.isEmpty()) {
					// If there are no entities to attack, that means other allies have killed the
					// opponent on this for loop.
					continue;
				}

				System.out.println("============================================");
				System.out.println(e.getClass().getSimpleName().toUpperCase() + "\n");

				e.getBattleAttributes().printBattleAttributesInfo();

				System.out.println("\nOpponents:");
				for (Battleable o : entitiesToAttack) {
					System.out.println(" - " + o.getClass().getSimpleName());
				}

				System.out.println("\n--------------------------------------------");

				e.attackOpponents(battleEntities);
            }

            // Remove all dead entities from the battle AFTER each entity had their turn attacking.
            List<Battleable> deadEntities = battleEntities.stream()
                .filter(e -> !e.isAlive())
                .collect(Collectors.toList());
            battleEntities.removeAll(deadEntities);
        }

		// If there are still enemies in the battleEnemies list (which can happen if there are tranced enemies),
		// then we add them to the list of dead enemies too.
		for (Battleable e : battleEnemies) {
			killEnemy(e);
			deadEnemies.add(e);
		}

		// Remove dead allied soldiers from the character.
		character.removeDeadAlliedSoldiers();

		return deadEnemies;
    }

	/**
     * Kill an enemy.
     * @param enemy enemy to be killed
     */
    public void killEnemy(Battleable enemy) {
        enemy.destroy();
        enemies.remove(enemy);
    }

	public void moveEnemies() {
		for (Battleable e : enemies) {
			e.move();
		}

		// If any enemies are killed on move, then we clear them.
		removeDeadEnemies();
	}

	public void removeDeadEnemies() {
		List<Enemy> deadEnemies = new ArrayList<>();
		for (Enemy e : enemies) {
			if (!e.isAlive()) {
				deadEnemies.add(e);
			}
		}

		for (Enemy e : deadEnemies) {
			e.destroy();
		}

		enemies.removeAll(deadEnemies);
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

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

}
