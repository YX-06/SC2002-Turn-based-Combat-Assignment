# SC2002 Turn-based Combat Assignment

## Overview
This is a Java Console Turn-Based Game Project for the submission of SC2002 Object Oriented Programming.
The player can select his class, items, level and the actions that the character does in game.

Although we do not have a lot of features, the premise of the project was to focus on OO Principles and thus, we made it as extensible as possible, following SOLID Principles.

## Gameplay Flow
CURRENT IMPLEMENTATIONS:
1. Choose between player classes `Warrior` or `Wizard`
2. Choose 2 items (duplicates allowed): `Potion`, `Smoke Bomb`, `Power Stone`
3. Choose difficulty between `Easy`, `Medium`, `Hard`
4. Round System Battles with the player choosing what action to take
5. When game ends, choose to replay with same settings, start a new game, or exit the game

## Core Combat Rules
- Currently, only game setting that we have sets turn order by speed (however, it is extensible to have more than that)
- Basic damage formula:
  - `damage = max(0, attacker.ATK - target.DEF)`
  - Status effects can modify incoming damage and return it to player
- Status effects tick during each round
- Stunned combatants cannot move in that turn(s) until the status wears off
- `Medium` and `Hard` ihave backup enemies that spawn after initial wave has been cleared

## Player Classes
- `Warrior`
  HP 260, ATK 40, DEF 20, SPD 30
  Special Skill: `Shield Bash` (Single Target Damage), cooldown - 3 rounds
  Single Target Damage with a stun effect on the enemy
  

- `Wizard`
  HP 200, ATK 50, DEF 10, SPD 20
  Special Skill: `Arcane Blast` (AOE damage), cooldown - 3 rounds
  Gains permanent `+10 ATK` permanent buff with every enemy killed (stacked per kill in that round NOT calculated at the end -> next enemy hit with extra damage)

## Items
- `Potion`: Heals 100 HP (max at MaxHP)
- `Smoke Bomb`: Enemy does 0 damage to player for 2 turns
- `Power Stone`: Triggers special skill without affecting/triggering cooldown

## Enemies and Difficulty
- Enemy types:
  - `Goblin`: HP 55, ATK 35, DEF 15, SPD 25
  - `Wolf`: HP 40, ATK 45, DEF 5, SPD 35

- Difficulty waves:
  - `Easy`: 3 Goblins
  - `Medium`: Goblin + Wolf, then backup 2 Wolves
  - `Hard`: 2 Goblins, then backup Goblin + 2 Wolves

## Project Structure
```text
src/
  Main.java
  boundary/   # Console UI (GameMenuUI, BattleUI, GameOVerUI)
  control/    # Battle Flow + Enemy AI + Turn Order Calculation
  entity/
    action/   # Action interfaces + Implementations
    combat/   # Player, Enemy, concrete combatants
    effect/   # Status effect implementations
    item/     # Item implementations
    result/   # Action result payloads (damage/effects/messages)
docs/
  FinalclassDiagram.drawio.png    # Class Diagram of How Classes Work Together
  sequence_diagram_round1.svg     # Seqeuence Diagrams to Showcase Round Flow
  sequence_diagram_round2.svg
  sequence_diagram_round3.svg
  sequence_diagram_round4.svg
```

## Run Locally
### Requirements
- Java JDK 17 or newer (JDK 21 also works)

### PowerShell (Windows)
```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName })
java -cp out Main
```

### Bash (macOS/Linux)
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out Main
```


