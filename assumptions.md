# Assumptions

- Cannot zombie bite a tranced enemy.
- Cannot trance a zombified ally.
- Tranced zombies that use ZombieBite on enemies will still keep that infected enemy in the EnemyState. When the ZombieBite ends, it will revert back to the battle state that it was in before.