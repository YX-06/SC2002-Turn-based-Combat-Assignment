# SC2002 Turn-based Combat Assignment

Draft README for the SC2002 OOP turn-based combat project.

## Overview
This project is a console-based turn-based combat game written in Java.  
The player selects a class, chooses two single-use items, picks a difficulty, and fights enemy waves until either the player is defeated or all enemies are eliminated.

## Gameplay Flow
1. Choose a player class: `Warrior` or `Wizard`.
2. Choose 2 items (duplicates allowed): `Potion`, `Smoke Bomb`, `Power Stone`.
3. Choose difficulty: `Easy`, `Medium`, `Hard`.
4. Battle round-by-round using actions and target selection.
5. On game end, choose to replay with same settings, start a new game, or exit.

## Core Combat Rules
- Turn order is determined each round by speed (higher `SPD` acts first).
- Basic damage formula:
  - `damage = max(0, attacker.ATK - target.DEF)`
  - Status effects can modify incoming damage after that.
- Status effects tick at the start of each combatant's turn.
- If a combatant is stunned, they lose their turn.
- `Medium` and `Hard` include backup enemy waves after the initial wave is defeated.

## Player Classes
- `Warrior`  
  HP 260, ATK 40, DEF 20, SPD 30  
  Special Skill: `Shield Bash` (single-target damage + 2-turn stun), cooldown 3 rounds.

- `Wizard`  
  HP 200, ATK 50, DEF 10, SPD 20  
  Special Skill: `Arcane Blast` (AOE damage), cooldown 3 rounds.  
  Gains permanent `+10 ATK` per enemy eliminated by Arcane Blast.

## Items
- `Potion`: Heals 100 HP (capped at max HP).
- `Smoke Bomb`: Enemy attacks deal 0 damage for 2 turns.
- `Power Stone`: Triggers your special skill for a free extra use without changing cooldown.

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
  boundary/   # Console UI (menu, battle, game-over screens)
  control/    # Battle flow, setup, AI strategy, turn-order strategy
  entity/
    action/   # Action interfaces and implementations
    combat/   # Player, Enemy, concrete combatants
    effect/   # Status effect implementations
    item/     # Item implementations
    result/   # Action result payloads (damage/effects/messages)
docs/
  FinalclassDiagram.drawio.png
  sequence_diagram_round1.svg
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

## Notes
- This is a draft README and can be expanded with screenshots, sample gameplay logs, and team/contributor details.
