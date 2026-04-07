package boundary;

import java.util.Scanner;

// Main menu screen — displays options and collects player setup choices.
// Separated from battle logic (SRP, UI separated from engine).
public class GameMenuUI {
    private Scanner scanner;

    public GameMenuUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayWelcome() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("         TURN-BASED COMBAT ARENA");
        System.out.println("==================================================");
    }

    public void displayPlayerClasses() {
        System.out.println();
        System.out.println("--- Player Classes ---");
        System.out.println("1. Warrior  | HP: 260 | ATK: 40 | DEF: 20 | SPD: 30");
        System.out.println("   Special: Shield Bash (BasicAttack dmg + Stun 2 turns)");
        System.out.println("2. Wizard   | HP: 200 | ATK: 50 | DEF: 10 | SPD: 20");
        System.out.println("   Special: Arcane Blast (dmg all enemies, +10 ATK per kill)");
    }

    public void displayEnemyTypes() {
        System.out.println();
        System.out.println("--- Enemy Types ---");
        System.out.println("Goblin  | HP: 55  | ATK: 35 | DEF: 15 | SPD: 25");
        System.out.println("Wolf    | HP: 40  | ATK: 45 | DEF: 5  | SPD: 35");
    }

    public void displayDifficultyLevels() {
        System.out.println();
        System.out.println("--- Difficulty Levels ---");
        System.out.println("1. Easy   - 3 Goblins");
        System.out.println("2. Medium - 1 Goblin + 1 Wolf  | Backup: 2 Wolves");
        System.out.println("3. Hard   - 2 Goblins          | Backup: 1 Goblin + 2 Wolves");
    }

    public void displayItemList() {
        System.out.println();
        System.out.println("--- Items (pick 2, duplicates allowed) ---");
        System.out.println("1. Potion      - Heal 100 HP (capped at max)");
        System.out.println("2. Smoke Bomb  - Enemy attacks deal 0 dmg for 2 turns");
        System.out.println("3. Power Stone - Free extra use of special skill (no cooldown change)");
    }

    public int promptPlayerClass() {
        System.out.print("\nSelect player class (1-2): ");
        return readInt(1, 2);
    }

    public int promptItem(int itemNumber) {
        System.out.print("Select item " + itemNumber + " (1-3): ");
        return readInt(1, 3);
    }

    public int promptDifficulty() {
        System.out.print("\nSelect difficulty (1-3): ");
        return readInt(1, 3);
    }

    private int readInt(int min, int max) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                int input = Integer.parseInt(line);
                if (input >= min && input <= max) return input;
            } catch (NumberFormatException e) {
                // fall through to re-prompt
            }
            System.out.print("Invalid input. Enter " + min + "-" + max + ": ");
        }
    }
}
