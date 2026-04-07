package control;

import entity.*;
import boundary.BattleUI;
import java.util.ArrayList;
import java.util.List;

// Core game-loop controller. Manages rounds, turn order, actions, and win/loss conditions.
// Depends on abstractions (TurnOrderStrategy, EnemyActionStrategy) for extensibility (DIP).
public class BattleEngine {
    private Player player;
    private Level level;
    private BattleContext context;
    private BattleUI battleUI;
    private TurnOrderStrategy turnOrderStrategy;
    private EnemyActionStrategy enemyAI;
    private int roundNo;

    public BattleEngine(Player player, Level level, BattleUI battleUI) {
        this.player = player;
        this.level = level;
        this.battleUI = battleUI;
        this.turnOrderStrategy = new SpeedBasedTurnOrder();
        this.enemyAI = new BasicEnemyAI();
        this.roundNo = 0;
        this.context = new BattleContext(player, level.getAllEnemies(), level);
    }

    // Main gameplay loop — runs rounds until player defeated or all enemies defeated.
    // Returns true if the player won, false if defeated.
    public boolean startBattle() {
        battleUI.displayBattleStart(player, level);

        while (true) {
            roundNo++;

            // Build turn order from alive combatants
            List<Combatant> aliveCombatants = getAliveCombatants();
            List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(aliveCombatants);

            battleUI.displayRoundHeader(roundNo, turnOrder);

            // STEP 1: Tick status effects for all alive combatants
            for (Combatant c : turnOrder) {
                c.tickEffects();
            }

            // STEP 2: Check if player alive after effects
            if (!player.isAlive()) {
                battleUI.displayEliminated(player);
                return false;
            }

            boolean gameEnded = false;
            boolean playerWon = false;

            // STEP 3: Each combatant acts in turn order
            for (Combatant c : turnOrder) {
                if (!c.isAlive()) {
                    continue;
                }

                if (!c.canAct()) {
                    battleUI.displayStunSkip(c);
                    // Stunned: do NOT decrement cooldown
                    continue;
                }

                if (c instanceof Enemy) {
                    handleEnemyTurn((Enemy) c);

                    if (!player.isAlive()) {
                        battleUI.displayEliminated(player);
                        gameEnded = true;
                        playerWon = false;
                        break;
                    }
                } else if (c instanceof Player) {
                    boolean usedSpecialSkill = handlePlayerTurn();

                    // Cooldown management: decrement only if NOT used special skill
                    if (!usedSpecialSkill) {
                        player.decrementCooldown();
                    }

                    // Check victory condition
                    if (level.getAliveEnemies().isEmpty()) {
                        if (!level.hasBackup() || level.isBackupSpawned()) {
                            gameEnded = true;
                            playerWon = true;
                            break;
                        } else {
                            // Spawn backup wave
                            List<Enemy> backup = level.spawnBackupWave();
                            context.setEnemies(level.getAllEnemies());
                            battleUI.displayBackupSpawn(backup);
                        }
                    }
                }
            }

            if (gameEnded) {
                return playerWon;
            }

            // STEP 4: End-of-round summary
            battleUI.displayTurnSummary(player, level.getAllEnemies());
        }
    }

    // Handle an enemy's turn: choose and execute an action via the AI strategy.
    private void handleEnemyTurn(Enemy enemy) {
        Action action = enemyAI.chooseAction(enemy, context);
        ActionResult result = action.execute(context);
        battleUI.displayActionResult(result);
    }

    // Handle the player's turn: prompt for action and execute.
    // Returns true if the player used their special skill (directly or via PowerStone),
    // meaning cooldown should NOT be decremented.
    private boolean handlePlayerTurn() {
        List<Enemy> aliveEnemies = level.getAliveEnemies();

        while (true) {
            int actionChoice = battleUI.promptActionChoice(player, aliveEnemies);

            switch (actionChoice) {
                case 1: { // BasicAttack
                    int targetIdx = 0;
                    if (aliveEnemies.size() > 1) {
                        targetIdx = battleUI.promptTarget(aliveEnemies);
                    }
                    Action action = new BasicAttackAction(player, aliveEnemies.get(targetIdx));
                    ActionResult result = action.execute(context);
                    battleUI.displayActionResult(result);
                    return false;
                }

                case 2: { // Defend
                    Action action = new DefendAction(player);
                    ActionResult result = action.execute(context);
                    battleUI.displayActionResult(result);
                    return false;
                }

                case 3: { // Item
                    List<Item> usableItems = player.getUsableItems();
                    if (usableItems.isEmpty()) {
                        battleUI.displayMessage("No items available! Choose another action.");
                        continue;
                    }
                    int itemIdx = battleUI.promptItemChoice(usableItems);
                    if (itemIdx == -1) {
                        continue; // back to action menu
                    }
                    Item selectedItem = usableItems.get(itemIdx);

                    // PowerStone needs a target for Warrior's Shield Bash
                    if (selectedItem instanceof PowerStone) {
                        if (player instanceof Warrior) {
                            int targetIdx = 0;
                            if (aliveEnemies.size() > 1) {
                                targetIdx = battleUI.promptTarget(aliveEnemies);
                            }
                            ((PowerStone) selectedItem).setTarget(aliveEnemies.get(targetIdx));
                        } else {
                            ((PowerStone) selectedItem).setTarget(null);
                        }
                    }

                    ActionResult result = selectedItem.use(player, context);
                    selectedItem.markConsumed();
                    battleUI.displayActionResult(result);
                    return result.isUsedSpecialSkill(); // PowerStone → true
                }

                case 4: { // SpecialSkill
                    if (!player.canUseSpecialSkill()) {
                        battleUI.displayCooldownMessage(player.getCooldown());
                        continue;
                    }

                    Combatant target = null;
                    if (player instanceof Warrior) {
                        int targetIdx = 0;
                        if (aliveEnemies.size() > 1) {
                            targetIdx = battleUI.promptTarget(aliveEnemies);
                        }
                        target = aliveEnemies.get(targetIdx);
                    }

                    ActionResult result = player.executeSpecialSkill(target, context);
                    player.setCooldown(3);
                    battleUI.displayActionResult(result);
                    return true;
                }

                default:
                    continue;
            }
        }
    }

    // Build a list of all alive combatants (player + enemies).
    private List<Combatant> getAliveCombatants() {
        List<Combatant> alive = new ArrayList<>();
        if (player.isAlive()) alive.add(player);
        for (Enemy e : level.getAllEnemies()) {
            if (e.isAlive()) alive.add(e);
        }
        return alive;
    }

    public int getRoundNo() { return roundNo; }
    public Player getPlayer() { return player; }
}
