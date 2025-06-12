# Assumptions

The list of concrete assumptions about game mechanics or entity attributes that are not specified in the specification (inside README.md).

## Battles

- Battles are turn-based systems wherein each entity gets to attack all its opponents in one go. This continues until all entities in the battle has had their turn attacking once.
- Entities added or removed from a battle are reflected in the next round.

## Stunned Effect

- A stunned opponent cannot attack until they are hit twice.

> [!WARNING]
> This is subject to change. Take with a grain of salt.

## Enemies

### Vampires

- Vampire bite (which deals additional damage) can only be triggered by vampire attacks.
- Vampire bites last between 2-4 hits and deal between 4-6 damage.

### Zombies

- The character is immune from a zombie bite.
- Cannot zombie bite a tranced enemy.
- Cannot trance a zombified ally.
- Tranced zombies that use ZombieBite on enemies will still keep that infected enemy in the EnemyState. When the ZombieBite ends, it will revert back to the battle state that it was in before.
- Infected entities with a zombie bite also gain +3 damage to their attacks.

### Bosses

- Immune from trance effect.

### Elan Muske

- 50% chance to heals all enemies by 5 health during a battle round each time they attack.

## Items

### Armour

- Halves both regular and boss damage.

### The One Ring

- Can only have an effect when right-clicked, in which the character now permanently holds the effect until the point at which they die and the effect will activate.
- One time use.

