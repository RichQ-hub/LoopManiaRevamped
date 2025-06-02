package unsw.loopmania.buildings;

import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;

public class TowerBuilding extends Building implements Battleable {

	private static final int LIFESPAN = 3;

	public TowerBuilding(Pair<Integer, Integer> position) {
		super(position, LIFESPAN);
	}

	@Override
	public void addToBuildingManager(BuildingManager manager) {
		manager.addBuilding(this);
		manager.addBattleBuilding(this);
	}

	@Override
	public void removeFromBuildingManager(BuildingManager manager) {
		manager.removeBuilding(this);
		manager.removeBattleBuilding(this);
	}

	// ==================================================================================
	// Battleable Methods.
	// ==================================================================================

	@Override
	public void attackOpponents(List<Battleable> battleEntities) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'attackOpponents'");
	}

	@Override
	public Attack buildAttack() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'buildAttack'");
	}

	@Override
	public void takeAttack(Attack attack) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'takeAttack'");
	}

	@Override
	public boolean isEnemy() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isEnemy'");
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isAlive'");
	}

	@Override
	public boolean isWithinBattleRadius(Character character) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isWithinBattleRadius'");
	}

	@Override
	public boolean isWithinSupportRadius(Character character) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isWithinSupportRadius'");
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'move'");
	}

	@Override
	public Loot dropLoot() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'dropLoot'");
	}

	@Override
	public void printInfo() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'printInfo'");
	}

	@Override
	public List<Battleable> getEntitiesToAttack(List<Battleable> battleEntities) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getEntitiesToAttack'");
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addToBattleManager'");
	}

	@Override
	public BattleAttributes getBattleAttributes() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getBattleAttributes'");
	}	
}
