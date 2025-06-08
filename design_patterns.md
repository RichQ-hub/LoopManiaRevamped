# Design Patterns

## 1. Goals (Composite Pattern)

Goals follow a tree-like structure, which can recursively calculate the completion of all the goals.

## 2. Location Observers (Observer Pattern)

Several buildings track character or enemy positions to affect them if they are in range. For example, the campfire building tracks the character position so that if the Character is within its range, it applies a double damage buff so long as they are within its range.

## 3. Battle State (State Pattern)

Represents whether a battle entity is an ally or an enemy. This is useful for choosing opponents during a battle.

## 4. Effect Modifiers (Visitor Pattern)

Effect modifiers avoid the need for the use of `instanceof` in code. Concrete implementations of effect modifiers (i.e. DamageModifier) can be applied to each element of a effects list, and the modifier object will know which effect it can apply to using **polymrphism** and **double dispatch** instead of troublesome conditional logic (e.g. if-statements to find the right object).

## 5. Attack (Command Pattern)

During the battle phase, an attack object is sent from the attacker to the opponent, which contains all the necessary effects that is to be applied to the opponent.

**Open/Closed Principle**

We can introduce new effects to the opponent without breaking existing code structure.

**Single Responsibility Principle**

Decouples effect objects from the attack objects.
