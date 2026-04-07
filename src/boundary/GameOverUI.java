package boundary;

import entity.*;
import java.util.List;
import java.util.Scanner;

// Game completion screen — shows victory/defeat stats and post-game options.
public class GameOverUI {
    private Scanner scanner;

    public GameOverUI(Scanner scanner) {
        this.scanner = scanner;
    }

    // Display victory screen with remaining HP and total rounds played.
    public void showVictoryScreen(Player player, int totalRounds) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  VICTORY!");
        System.out.println("==================================================");
        System.out.println("Congratulations, you have defeated all your enemies.");
        System.out.println();
        System.out.println("Statistics:");
        System.out.println("  Remaining HP: " + player.getHp() + "/" + player.getMaxHP());
        System.out.println("  Total Rounds: " + totalRounds);
        printItemStatus(player);
        if (player instanceof Wizard) {
            System.out.println("  Final ATK: " + player.getAtk());
        }
    }

    // Display defeat screen with enemies remaining and rounds survived.
    public void showDefeatScreen(Player player, List<Enemy> enemies, int totalRounds) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  DEFEATED");
        System.out.println("==================================================");
        System.out.println("Defeated. Don't give up, try again!");
        System.out.println();
        int remaining = 0;
        for (Enemy e : enemies) {
            if (e.isAlive()) remaining++;
        }
        System.out.println("Statistics:");
        System.out.println("  Enemies Remaining: " + remaining);
        System.out.println("  Rounds Survived: " + totalRounds);
    }

    private void printItemStatus(Player player) {
        for (Item item : player.getInventory()) {
            String status = item.isConsumed() ? "consumed" : "unused";
            System.out.println("  " + item.getName() + ": " + status);
        }
    }

    // Prompt user for post-game choice: Replay / New Game / Exit.
    // Returns 1=Replay, 2=New Game, 3=Exit.
    public int promptPostGameChoice() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("  1. Replay (same settings)");
        System.out.println("  2. New Game (return to home)");
        System.out.println("  3. Exit");
        System.out.print("Choice (1-3): ");
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                int input = Integer.parseInt(line);
                if (input >= 1 && input <= 3) return input;
            } catch (NumberFormatException e) {
                // fall through
            }
            System.out.print("Invalid input. Enter 1-3: ");
        }
    }
}
