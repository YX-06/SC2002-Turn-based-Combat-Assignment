package control;

import boundary.BattleUI;
import entity.*;
import entity.action.ItemAction;
import entity.combat.Enemy;
import entity.combat.Player;
import entity.effect.StatusEffect;
import entity.item.Item;
import entity.result.ActionResult;

import java.util.ArrayList;
import java.util.List;

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

    public boolean startBattle() {
        battleUI.displayBattleStart(player, level);

        while (true) {
            roundNo++;

            List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(getAliveCombatants());
            battleUI.displayRoundHeader(roundNo, turnOrder);

            for (Combatant c : turnOrder) {
                if (!c.isAlive()) continue;

                Action executedAction;

                if (c instanceof Player) {
                    executedAction = handlePlayerTurn();

                    // Cooldown handled ONLY here
                    player.applyCooldown(executedAction.getCooldownCost());

                } else {
                    executedAction = handleEnemyTurn((Enemy) c);
                }

                if (checkGameEnd()) {
                    return !level.getAliveEnemies().isEmpty()
                            ? false
                            : handleBackupOrWin();
                }
            }

            battleUI.displayTurnSummary(player, level.getAllEnemies());
        }
    }

    private Action handlePlayerTurn() {
        List<Enemy> aliveEnemies = level.getAliveEnemies();

        while (true) {
            List<Action> actions = player.getActions();
            int choice = battleUI.promptActionChoice(actions, aliveEnemies);

            if (choice < 0 || choice >= actions.size()) continue;

            Action action = actions.get(choice);

            // Item handling (selection only, no logic here)
            if (action instanceof ItemAction itemAction) {
                List<Item> items = itemAction.getUsableItems();
                int itemChoice = battleUI.promptItemChoice(items);
                if (itemChoice == -1) continue;
                itemAction.setSelectedItem(items.get(itemChoice));
            }

            List<Combatant> targets = resolveTargets(action, aliveEnemies);

            ActionResult result = action.execute(player, targets);

            applyActionResult(result, player);

            battleUI.displayActionResult(result);
            return action;
        }
    }

    private Action handleEnemyTurn(Enemy enemy) {
        Action action = enemyAI.chooseAction(enemy, context);

        List<Combatant> targets = List.of(player);

        ActionResult result = action.execute(enemy, targets);

        applyActionResult(result, enemy);

        battleUI.displayActionResult(result);
        return action;
    }

    private List<Combatant> resolveTargets(Action action, List<Enemy> aliveEnemies) {

        // No target required (self / defend)
        if (!action.requiresTarget()) {
            if (action.isAOE()) {
                return new ArrayList<>(aliveEnemies);
            }
            return List.of();
        }

        // Single target
        if (aliveEnemies.size() == 1) {
            return List.of(aliveEnemies.get(0));
        }

        int targetIdx = battleUI.promptTarget(aliveEnemies);
        return List.of(aliveEnemies.get(targetIdx));
    }

    private void applyActionResult(ActionResult result, Combatant user) {

        // Apply damage
        if (result.getTarget() != null && result.getDamage() > 0) {
            result.getTarget().takeDamage(result.getDamage());
        }

        // Apply effects
        Combatant target = result.getTarget() != null ? result.getTarget() : user;

        for (StatusEffect effect : result.getEffects()) {
            target.addStatusEffect(effect);
        }
    }

    private boolean checkGameEnd() {
        return !player.isAlive() || level.getAliveEnemies().isEmpty();
    }

    private boolean handleBackupOrWin() {
        if (level.getAliveEnemies().isEmpty()) {
            if (level.hasBackup() && !level.isBackupSpawned()) {
                List<Enemy> backup = level.spawnBackupWave();
                context.setEnemies(level.getAllEnemies());
                battleUI.displayBackupSpawn(backup);
                return false;
            }
            battleUI.displayVictory(player, roundNo);
            return true;
        }
        battleUI.displayEliminated(player);
        return true;
    }

    private List<Combatant> getAliveCombatants() {
        List<Combatant> alive = new ArrayList<>();
        if (player.isAlive()) alive.add(player);
        for (Enemy e : level.getAllEnemies()) {
            if (e.isAlive()) alive.add(e);
        }
        return alive;
    }
}