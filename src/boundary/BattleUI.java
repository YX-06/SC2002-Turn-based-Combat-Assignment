package boundary;

import entity.*;
import entity.action.Action;
import entity.action.DefendAction;
import entity.action.ItemAction;
import entity.action.SpecialSkillAction;
import entity.combat.Combatant;
import entity.combat.Enemy;
import entity.combat.Player;
import entity.item.Item;
import entity.result.ActionResult;
import java.util.List;
import java.util.Scanner;

// Displays battle information and collects player action choices during combat
// Handles all in-battle display and input (UI only, no game logic)
public class BattleUI {
    private Scanner scanner;

    public BattleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayBattleStart(Player player, Level level) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  BATTLE START! Difficulty: " + level.getDifficulty());
        System.out.println("==================================================");
        System.out.println("Player: " + player.getName()
                + " | HP: " + player.getHp() + "/" + player.getMaxHP()
                + " | ATK: " + player.getAtk()
                + " | DEF: " + player.getDef()
                + " | SPD: " + player.getSpeed());
        System.out.print("Items: ");
        List<Item> inv = player.getInventory();
        for (int i = 0; i < inv.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(inv.get(i).getName());
        }
        System.out.println();
        System.out.println("Enemies:");
        for (Enemy e : level.getAllEnemies()) {
            System.out.println("  " + e.getName()
                    + " | HP: " + e.getHp()
                    + " | ATK: " + e.getAtk()
                    + " | DEF: " + e.getDef()
                    + " | SPD: " + e.getSpeed());
        }
        System.out.println("==================================================");
    }

    // Display the round header with round number and turn order
    public void displayRoundHeader(int roundNo, List<Combatant> turnOrder) {
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("  Round " + roundNo);
        System.out.println("--------------------------------------------------");
        System.out.print("Turn Order: ");
        for (int i = 0; i < turnOrder.size(); i++) {
            if (i > 0) System.out.print(" → ");
            Combatant c = turnOrder.get(i);
            System.out.print(c.getName() + " (SPD " + c.getSpeed() + ")");
        }
        System.out.println();
        System.out.println();
    }

    // Display the result of an action
    public void displayActionResult(ActionResult result) {
        System.out.println(result.getMessage());
    }

    // Display that a combatant has been eliminated
    public void displayEliminated(Combatant combatant) {
        System.out.println(combatant.getName() + " has been eliminated!");
    }

    // Display that a combatant's turn is skipped due to stun.
    public void displayStunSkip(Combatant combatant) {
        if (!combatant.isAlive()) {
            System.out.println(combatant.getName() + " → ELIMINATED: Skipped");
        } else {
            System.out.println(combatant.getName() + " → STUNNED: Turn skipped");
        }
    }

    // Display the SpecialSkill is on cooldown
    public void displayCooldownMessage(int remainingCD) {
        System.out.println("Special Skill unavailable! Cooldown: " + remainingCD + " round(s) remaining.");
    }

    // Display backup wave spawn noti
    public void displayBackupSpawn(List<Enemy> enemies) {
        System.out.println();
        System.out.println("*** BACKUP SPAWN! ***");
        for (Enemy e : enemies) {
            System.out.println("  " + e.getName() + " (HP: " + e.getHp()
                    + " | ATK: " + e.getAtk()
                    + " | DEF: " + e.getDef()
                    + " | SPD: " + e.getSpeed() + ") enters the battle!");
        }
        System.out.println();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    // Prompt player for an action choice
    public int promptActionChoice(Player player, List<Enemy> aliveEnemies) {
        System.out.println();
    System.out.println(player.getName() + "'s turn (HP: " 
        + player.getHp() + "/" + player.getMaxHP() + ")");

    List<Action> actions = player.getActions();

    for (int i = 0; i < actions.size(); i++) {
        Action action = actions.get(i);

        String line = "  " + (i + 1) + ". ";

        // NEVER call getName() for ItemAction
        if (action instanceof ItemAction) {
            line += "Item";
        } else {
            line += action.getName();
        }

        if (action instanceof DefendAction) {
            line += " (+10 DEF, 2 turns)";
        } 
        else if (action instanceof ItemAction) {
            if (player.hasUsableItems()) {
                line += " (";
                List<Item> usable = player.getUsableItems();

                for (int j = 0; j < usable.size(); j++) {
                    if (j > 0) line += ", ";
                    line += usable.get(j).getName();
                }

                line += ")";
            } else {
                line += " (none available)";
            }
        } 
        else if (action instanceof SpecialSkillAction) {
            if (player.canUseSpecialSkill()) {
                line += " [READY]";
            } else {
                line += " [Cooldown: " + player.getCooldown() + "]";
            }
        }

        System.out.println(line);
    }

    System.out.print("Choose action (1-" + actions.size() + "): ");
    return readInt(1, actions.size()) - 1;
    }

    // Prompt player to select a target enemy
    // Returns the index in the aliveEnemies list
    public int promptTarget(List<Enemy> aliveEnemies) {
        System.out.println("Select target:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            Enemy e = aliveEnemies.get(i);
            System.out.println("  " + (i + 1) + ". " + e.getName()
                    + " (HP: " + e.getHp() + "/" + e.getMaxHP() + ")");
        }
        System.out.print("Target (1-" + aliveEnemies.size() + "): ");
        return readInt(1, aliveEnemies.size()) - 1;
    }

    // Prompt player to select an item from usable items.
    // Returns the index in the usableItems list, or -1 to go back
    public int promptItemChoice(List<Item> usableItems) {
        System.out.println("Select item:");
        for (int i = 0; i < usableItems.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + usableItems.get(i).getName());
        }
        System.out.println("  0. Back");
        System.out.print("Choice (0-" + usableItems.size() + "): ");
        int choice = readInt(0, usableItems.size());
        return choice - 1; // -1 means back (0-1), 0+ means item index
    }

    // Display end-of-round summary (HP, effects, inventory, cooldowns)
    public void displayTurnSummary(Player player, List<Enemy> allEnemies) {
        System.out.println();
        System.out.println("--- End of Round ---");

        // Player status
        StringBuilder sb = new StringBuilder();
        sb.append(player.getName()).append(" HP: ").append(player.getHp()).append("/").append(player.getMaxHP());

        
        
        System.out.println(sb.toString());


        // Inventory
        System.out.print("Items: ");
        List<Item> inv = player.getInventory();
        boolean first = true;
        for (Item item : inv) {
            if (!first) System.out.print(" | ");
            System.out.print(item.getName() + ": " + (item.isConsumed() ? "consumed" : "1"));
            first = false;
        }
        System.out.println();

        // Cooldown
        System.out.println("Special Skills Cooldown: " + player.getCooldown() + " round(s)");
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
