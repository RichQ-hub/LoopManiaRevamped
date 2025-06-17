# LoopMania

LoopMania is a turn-based RPG where players guide a hero through looping maps filled with enemies, treasures, and tactical challenges. Inspired by roguelike and strategy games, LoopMania combines map-based progression with a robust battle and inventory system.

## Table of Contents
- [1. Gameplay Loop](#1-gameplay-loop)
		- [Hero's Castle](#heros-castle)
		- [Shop](#shop)
		- [Cards/Buildings](#cardsbuildings)
		- [Items](#items)
		- [Enemies](#enemies)
		- [Goals](#goals)
- [2. Core Systems](#2-core-systems)
	- [2.1 Maps](#21-maps)
	- [2.2 Battle System](#22-battle-system)
		- [Initiation](#initiation)
		- [Rounds](#rounds)
		- [Dead Entities](#dead-entities)
		- [New Entities](#new-entities)
	- [2.3 Inventory and Equipment](#23-inventory-and-equipment)
		- [Equipping Items](#equipping-items)
		- [Unequipping Items](#unequipping-items)
		- [Swapping Items](#swapping-items)
- [3. Game Entities](#3-game-entities)
	- [3.1 Enemies](#31-enemies)
	- [3.2 Items ⚔️](#32-items-️)
	- [3.3 Rare Items 🔱](#33-rare-items-)
	- [3.3 Buildings/Cards 🏛️](#33-buildingscards-️)
- [4. World Config](#4-world-config)
	- [4.1 Format](#41-format)
		- [4.1.1 Entities](#411-entities)
		- [4.1.2 Goal](#412-goal)
		- [4.1.3 Path](#413-path)

## 1. Gameplay Loop

#### Hero's Castle

The character always starts here, regardless of the map. The player can access the shop here at the end of every cycle.

#### Shop

The player can access the shop when the character enters the hero's castle at the start of every loop. The player can sell or buy items here.

#### Cards/Buildings

The player can obtain cards to spawn buildings as drops from enemies. They can be used for a variety of functions to dynamically alter the game state.

**Lifespan**

Buildings once spawned only last a specified number of rounds before they despawn. The player should effectively manage what buildings to spawn to adequately progress the game.

#### Items

Item management is a core mechanic in order to survive the onslaught of enemies during a round. Picking and choosing the right equipment BEFORE engaging an enemy in battle is key to winning in this game. For example, equipping a stake is highly effective in dispatching vampires during a battle, but may be deemed futile against other enemies.

#### Enemies

In order to progress through each cycle, you must equip your character with adequate resources (i.e. support buildings, allied soldiers, equipment) to face any enemy you may encounter.

#### Goals

To complete the game, you MUST achieve the required goals set for the current world.

> [!NOTE]
> The goal gets checked once the character enters back into the castle! Hence, even if the character achieves the goal during the middle of a cycle, the game will only complete once they enter back into the castle. Keep surviving through the round!

## 2. Core Systems

### 2.1 Maps

The game world is structured around a looping path that the hero continuously traverses. The map dynamically spawns enemies and resources as the player progresses. Each tile may represent terrain, an enemy encounter, a rest point, or an interactive structure. New elements are introduced with each loop, increasing difficulty and opportunities.

### 2.2 Battle System

LoopMania features a turn-based combat system where players and enemies take alternate turns attacking their opponents.

> [!IMPORTANT]
> The player cannot interact with the world state (such as equipping items, placing new buildings, etc) once a battle has started.

#### Initiation

The player character enters a battle once it enters the `battle radius` of an enemy. Any existing enemies on the map in the current cycle may join the initiated battle IF the character is within their `support radius` at the onset of the battle.

#### Rounds

For each round, each entity has the opportunity to attack ALL their opponents at ONCE. If an entity's opponent dies due to a previous entity killing them, then they become untargetable and simply cannot receive attacks.

> [!IMPORTANT]
> The battle round is **immutable**, meaning all entities in the current round cannot be removed, only ignored. Hence, all dead/new entities are filtered and relfected in the next round.

#### Dead Entities

At the end of each round, all dead entities are cleared from the battle and the next round doesn't include them.

#### New Entities

Should new combatants spawn during a battle round (e.g. a slime splitting into 2 baby slimes), then those new entities are added in the next round.

### 2.3 Inventory and Equipment

The player may equip their character with items (only those that are equippable) that can assist them during a battle.

#### Equipping Items

Simply drag an equipment item into its corresponding slot from the unequipped inventory.

#### Unequipping Items

Simply drag an equipped item back into the unequipped inventory pane (any tile will do) and the game will automatically place the unequipped item into the first available slot.

#### Swapping Items

The game can handle equipping an item into its corresponding spot even if it already contains an equipped item. 

## 3. Game Entities

### 3.1 Enemies

Possible enemy types are listed below:

| Enemy Type | Icon | Description | Spawn conditions |
|:-------------:|:-------:|:-----------:|:---------:|
| Slug | ![Slug](src/images/slug.png) | A standard enemy type. Low health and low damage. The battle radius is the same as the support radius for a slug. | Spawns randomly on path tiles |
| Zombie | ![Zombie](src/images/zombie.png) | *Braaaaaaiiiinnnnnssss!*<br/>Zombies have low health, moderate damage, and are slower compared to other enemies. A critical bite from a zombie against an allied soldier (which has a random chance of occurring) will transform the allied soldier into a zombie, which will then proceed to fight against the Character until it is killed. Zombies have a higher battle radius than slugs | Spawns from zombie pit every time the Character completes a cycle of the path |
| Vampire | ![Vampire](src/images/vampire.png) | *I vant to suck your blood!*<br/>Vampires have high damage, are susceptible to the *stake* weapon, and run away from campfires. They have a higher battle radius than slugs, and an even higher support radius. A critical bite (which has a random chance of occurring) from a vampire causes random additional damage with every vampire attack, for a random number of vampire attacks | Spawns from vampire castle every 5 cycles of the path completed by the Character |
| Doggie | ![Doggie](src/images/doggie.png) | *Wow much coin how money so crypto plz mine v rich very currency*<br/>A special boss which spawns the DoggieCoin upon defeat, which randomly fluctuates in sellable price to an extraordinary extent. It has high health and can stun the character, which prevents the character from making an attack temporarily. The battle and support radii are the same as for slugs | Spawns after 20 cycles |
| Elan Muske | ![Elan Muske](src/images/ElanMuske.png) | *To the moon!*<br/>An incredibly tough boss which, when appears, causes the price of DoggieCoin to increase drastically. Defeating this boss causes the price of DoggieCoin to plummet. Elan has the ability to heal other enemy NPCs. The battle and support radii are the same as for slugs | Spawns after 40 cycles, and the player has reached 10000 experience points |

### 3.2 Items ⚔️

Possible basic item types are listed below:

| Item Type | Icon | Description | Where can obtain |
|:-------------:|:-------:|:-----------:|:---------:|
| Sword | ![Sword](src/images/basic_sword.png) | A standard melee weapon. Increases damage dealt by Character | Purchase in Hero's Castle, loot from enemies, or obtained from cards lost due to being the oldest and replaced |
| Stake | ![Stake](src/images/stake.png) | A melee weapon with lower stats than the sword, but causes very high damage to vampires | Purchase in Hero's Castle, loot from enemies, or obtained from cards lost due to being the oldest and replaced |
| Staff | ![Staff](src/images/staff.png) | A melee weapon with very low stats (lower than both the sword and stake), which has a random chance of inflicting a *trance*, which transforms the attacked enemy into an allied soldier temporarily (and fights alongside the Character). If the trance ends during the fight, the affected enemy reverts back to acting as an enemy which fights the Character. If the fight ends whilst the enemy is in a trance, the enemy dies | Purchase in Hero's Castle, loot from enemies, or obtained from cards lost due to being the oldest and replaced |
| Armour | ![Armour](src/images/armour.png) | Body armour, provides defence and halves enemy attack | Purchase in Hero's Castle, loot from enemies, or obtained from cards lost due to being the oldest and replaced |
| Shield | ![Shield](src/images/shield.png) | Defends against enemy attacks, critical vampire attacks have a 60% lower chance of occurring | Purchase in Hero's Castle, loot from enemies, or obtained from cards lost due to being the oldest and replaced |
| Helmet | ![Helmet](src/images/helmet.png) | Defends against enemy attacks, enemy attacks are reduced by a scalar value. The damage inflicted by the Character against enemies is reduced (since it is harder to see) | Purchase in Hero's Castle, loot from enemies, or obtained from cards lost due to being the oldest and replaced |
| Gold | ![Gold](src/images/gold_pile.png) | Money, used to buy things | Obtain in Hero's Castle by selling items, loot from enemies, pick up off the ground, or obtained from cards/items lost due to being the oldest and replaced. Spawns randomly on path tiles |
| Health potion | ![Potion](src/images/brilliant_blue_new.png) | Refills Character health | Purchase from Hero's Castle, loot from enemies, pick up off the ground, or obtained from cards lost due to being the oldest and replaced. Spawns randomly on path tiles |
| DoggieCoin | ![DoggieCoin](src/images/doggiecoin.png) | A revolutionary asset type, which randomly fluctuates in sellable price to an extraordinary extent. Can sell at shop | Obtained when defeat Doggie |

Basic item types may be available in every game (receiving a particular item/items is based on random chance after winning a battle).

### 3.3 Rare Items 🔱

Every time the Character wins a battle, there is a small chance of winning a "Rare Item". The available rare items are specified in the world configuration file.

Rare items are listed below:

| Rare Item Type | Icon | Description|
|:-------------:|:-------:|:-----------:|
| The One Ring | ![The One Ring](src/images/the_one_ring.png) | If the Character is killed, it respawns with full health up to a single time |
| Anduril, Flame of the West | ![Anduril, Flame of the West](src/images/anduril_flame_of_the_west.png) | A very high damage sword which causes triple damage against bosses |
| Tree Stump | ![Tree Stump](src/images/tree_stump.png) | An especially powerful shield, which provides higher defence against bosses |

> [!NOTE]
> Note that **DoggieCoin** is not considered to be a rare item, since the player will have the opportunity to obtain DoggieCoin every game.

Rare item types will not be available in a game if it is not added to the world configuration file.

### 3.3 Buildings/Cards 🏛️

The following are the available building types for your project:

| Building Type | Icon | Card To Spawn Building | Description | Placement |
|:-------------:|:-------:|:---:|:-----------:|:---------:|
| Vampire castle | ![Vampire Castle](src/images/vampire_castle_building_purple_background.png) | ![Vampire Castle Card](src/images/vampire_castle_card.png) | Produces vampires every 5 cycles of the path completed by the Character, spawning nearby on the path | Only on non-path tiles adjacent to the path |
| Zombie pit | ![Zombie Pit](src/images/zombie_pit.png) | ![Zombie Pit Card](src/images/zombie_pit_card.png) | Produces zombies every cycle of the path completed by the Character, spawning nearby on the path | Only on non-path tiles adjacent to the path |
| Tower | ![Tower](src/images/tower.png) | ![Tower Card](src/images/tower_card.png) | During a battle within its shooting radius, enemies will be attacked by the tower | Only on non-path tiles adjacent to the path |
| Village | ![Village](src/images/village.png) | ![Village Card](src/images/village_card.png) | Character regains health when passing through | Only on path tiles |
| Barracks | ![Barracks](src/images/barracks.png) | ![Barracks Card](src/images/barracks_card.png) | Produces allied soldier to join Character when passes through | Only on path tiles |
| Trap | ![Trap](src/images/trap.png) | ![Trap Card](src/images/trap_card.png) | When an enemy steps on a trap, the enemy is damaged (and potentially killed if it loses all health) and the trap is destroyed | Only on path tiles |
| Campfire | ![Campfire](src/images/campfire.png) | ![Campfire Card](src/images/campfire_card.png) | Character deals double damage within campfire battle radius | Any non-path tile |
| Hero's Castle | ![Hero's Castle](src/images/heros_castle.png) | N/A | Character starts at the Hero's Castle, and upon finishing the required number of cycles of the path completed by the Character, when the Character enters this castle, the Human Player is offered a window to purchase items at the Hero's Castle | Exists at the starting position of the Character (not spawned by a card, always exists) |

## 4. World Config

Inside the `worlds/` folder, there you will find json files that specify the details of a specific game map that will be available for the player to choose from. Each map contains goals, pathways, and rare items that unique to that specific map.

### 4.1 Format

**Sample World File**

```json
{
  "width": 8,
  "height": 14,
  "rare_items": [],
  "goal-condition": {"goal": "AND", "subgoals":
		[
			{"goal": "cycles", "quantity": 100},
			{"goal": "OR", "subgoals":
				[
					{"goal": "gold", "quantity": 200},
					{"goal": "experience", "quantity": 300}
				]
			}
		]
	},
  "entities": [
    {"x": 0, "y": 0, "type": "hero_castle"}
  ],
  "path": {
    "type": "path_tile",
    "x": 0, "y": 0,
    "path": [
			"RIGHT", "RIGHT", "RIGHT", "RIGHT", "RIGHT", "RIGHT", "RIGHT",
			"DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN", "DOWN",
			"LEFT", "LEFT", "LEFT", "LEFT", "LEFT", "LEFT", "LEFT",
			"UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP", "UP"
    ]
  }
}
```

### 4.2 Entities

**Hero's Castle**

You must specify the coordinate location of the hero's castle on every map.

### 4.3 Goal

#### 4.3.1 Concrete Goals

|    Goal    | Quantity? | Description                                                              |
| :--------: | :-------: | ------------------------------------------------------------------------ |
|   `cycles`   |    ✅     | Specifies the amount of cycles required to complete this goal.           |
|    `gold`    |    ✅     | Specifies the amount of gold required to complete this goal.             |
| `experience` |    ✅     | Specifies the amount of experience required to complete this goal.       |
|   `bosses`   |    ❌     | Specifies that all bosses need to be killed for the goal to be achieved. |

**Format**

```json
	{"goal": "cycles", "quantity": 100}
```

#### 4.3.2 Composite Goals

| Goal |                             Description                             |
| :--: | :-----------------------------------------------------------------: |
| `AND`  |    Both 2 subgoals must be met in order for this goal to be met.    |
|  `OR`  | Either 1 or both subgoals must be met for this goal to be achieved. |

**Format**

```json
{
	"goal-condition": {"goal": "AND", "subgoals":
		[
			{"goal": "cycles", "quantity": 100},
			{"goal": "gold", "quantity": 200},
		]
	},
}
```

> [!IMPORTANT]
> The "subgoals" key must be an array containing 2 goal objects.

> [!NOTE]
> Complex goals can be constructed by building a tree of composite goals. So any 1 of the 2 subgoals can be a composite goal itself.

### 4.4 Path

Starting from the hero's castle, the player can specify the direction of the path throughout the map boundaries.

> [!WARNING]
> - The path MUST not overlap with existing path tiles.
> - The path MUST always loop back to the hero's castle.

**Available Options**

| Keywords |
| -------- |
| `UP`       |
| `DOWN`     |
| `LEFT`     |
| `RIGHT`    |

### 4.5 Map Class

Each json map file MUST be associated with a unique subclass of the `GameMap` class inside `src/unsw/loopmania/maps` specifying the official name of the map as well as it's filename.

Additionally, you must include the map as an option for play inside `MainMenuController.java` inside the constructor.

```java
public MainMenuController() {
	this.maps = new LinkedHashMap<>();

	// Specify maps here:
	GameMap originalMap = new OriginalMap();
	GameMap ringMap = new RingMap();
	GameMap bigRingMap = new BigRingMap();

	this.maps.put(originalMap.getMapName(), originalMap);
	this.maps.put(ringMap.getMapName(), ringMap);
	this.maps.put(bigRingMap.getMapName(), bigRingMap);

	// Initially the map is the original map.
	this.selectedMap = originalMap;
}
```

> [!NOTE]
> This is subject to change.
