package unsw.loopmania.buildings;

import java.util.List;

import org.javatuples.Pair;

import unsw.loopmania.battle.Attack;
import unsw.loopmania.battle.BattleAttributes;
import unsw.loopmania.battle.Battleable;
import unsw.loopmania.battle.battleState.AlliedState;
import unsw.loopmania.battle.battleState.BattleState;
import unsw.loopmania.battle.effects.DamageEffect;
import unsw.loopmania.battle.loot.Loot;
import unsw.loopmania.combatants.Character;
import unsw.loopmania.managers.BattleManager;
import unsw.loopmania.managers.BuildingManager;

public class TowerBuilding extends Building implements Battleable {

	private static final int LIFESPAN = -1;

	private BattleAttributes battleAttributes;

	public TowerBuilding(Pair<Integer, Integer> position) {
		super(position, LIFESPAN);
		super.setEntityImageByPath("src/images/tower.png");

		// Set battle attributes.
		BattleAttributes attr = new BattleAttributes(this, 1, 8, 0, new AlliedState());
		attr.addBaseAttackEffect(new DamageEffect(2));
		this.battleAttributes = attr;
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
	public void attackOpponents(List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound) {
		battleAttributes.attackOpponents(battleEntities, battleEntitiesInRound);
	}

	/**
	 * Takes no damage.
	 */
	@Override
	public void takeAttack(Attack attack, List<Battleable> battleEntities, List<Battleable> battleEntitiesInRound) {
		return;
	}

	@Override
	public Attack buildAttack() {
		Attack attack = battleAttributes.buildAttack();
		return attack;
	}

	@Override
	public boolean isEnemy() {
		return battleAttributes.getBattleState().isEnemy();
	}

	@Override
	public boolean isAlive() {
		return battleAttributes.getHealth() > 0;
	}

	@Override
	public boolean isWithinBattleRadius(Character character) {
		return battleAttributes.isWithinBattleRadius(character);
	}

	@Override
	public boolean isWithinSupportRadius(Character character) {
		return false;
	}

	@Override
	public void move() {
		return;
	}

	@Override
	public Loot dropLoot() {
		return null;
	}

	@Override
	public void printInfo() {
		battleAttributes.printBattleAttributesInfo();
	}

	@Override
	public List<Battleable> getEntitiesToAttack(List<Battleable> battleEntities) {
		BattleState state = battleAttributes.getBattleState();
		return state.getOpponents(battleEntities);
	}

	@Override
	public void addToBattleManager(BattleManager manager) {
		return;
	}

	@Override
	public BattleAttributes getBattleAttributes() {
		return battleAttributes;
	}
}
