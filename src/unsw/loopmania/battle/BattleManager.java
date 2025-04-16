package unsw.loopmania.battle;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.buildings.Building;
import unsw.loopmania.combatants.Combatant;
import unsw.loopmania.entity.Battleable;

public class BattleManager {
	private Character character;
	private List<Combatant> enemies;
	private List<Building> buildings;

	public BattleManager(Character character) {
		this.character = character;
		this.enemies = new ArrayList<>();
		this.buildings = new ArrayList<>();
	}

	/**
	 * Double dispatch method.
	 * @param entity
	 */
	public void addBattleableEntity(Battleable entity) {
		entity.addToBattleManager(this);
	}

	public void addEnemy(Combatant enemy) {
		enemies.add(enemy);
	}

	public void addBuilding(Building building) {
		buildings.add(building);
	}
}
