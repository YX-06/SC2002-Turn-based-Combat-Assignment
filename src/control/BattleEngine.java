package control;

import boundary.BattleUI;
import entity.*;
import entity.action.Action;
import entity.action.ItemAction;
import entity.combat.Combatant;
import entity.combat.Enemy;
import entity.combat.Player;
import entity.effect.StatusEffect;
import entity.item.Item;
import entity.item.PowerStone;
import entity.result.ActionResult;
import entity.result.DamageInstance;
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

    public int getRoundNo() {
        return roundNo;
    }
    public boolean startBattle() {
        battleUI.displayBattleStart(player, level);

        while (true) {
            roundNo++;

            List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(getAliveCombatants());
            battleUI.displayRoundHeader(roundNo, turnOrder);

            for (Combatant c : turnOrder) {
                if (!c.isAlive()) continue;

                // Check turn-preventing effects before reducing duration.
                if (!c.canAct()) {
                    battleUI.displayStunSkip(c);
                    c.tickEffects();

                    if (checkGameEnd()) {
                        if (!player.isAlive()) {
                            return false;
                        }

                        if (handleBackupOrWin()) {
                            return true;
                        }

                        continue;
                    }

                    continue;
                }

                // Advance normal timed effects at the start of the combatant's turn
                c.tickEffects();

                if (!c.isAlive()) {
                    if (checkGameEnd()) {
                        if (!player.isAlive()) {
                            return false;
                        }

                        if (handleBackupOrWin()) {
                            return true;
                        }

                        continue;
                    }
                }

                Action executedAction;

                if (c instanceof Player) {
                    executedAction = handlePlayerTurn();

                    // Cooldown handled ONLY here
                    player.applyCooldown(executedAction.getCooldownCost());

                } else {
                    executedAction = handleEnemyTurn((Enemy) c);
                }

                if (checkGameEnd()) {
                    if (!player.isAlive()) {
                        return false;
                    }

                    if (handleBackupOrWin()) {
                        return true;
                    }

                    continue;
                }
            }

            battleUI.displayTurnSummary(player, level.getAllEnemies());
        }
    }

    private Action handlePlayerTurn() {
        List<Enemy> aliveEnemies = context.getAliveEnemies();

        while (true) {
            List<Action> actions = player.getActions();
            int choice = battleUI.promptActionChoice(player, aliveEnemies);

            if (choice < 0 || choice >= actions.size()) continue;

            Action action = actions.get(choice);

            // Item handling (selection only, no logic here)
            if (action instanceof ItemAction itemAction) {
                List<Item> items = player.getUsableItems();
                int itemChoice = battleUI.promptItemChoice(items);
                if (itemChoice == -1) continue;
                itemAction.setSelectedItem(items.get(itemChoice));
            }
            
            if (!action.canExecute(player)) {
                if (!player.canUseSpecialSkill()) {
                    battleUI.displayCooldownMessage(player.getCooldown());
                } else {
                    battleUI.displayMessage("Action cannot be executed.");
                }
                continue;
            }


            List<Combatant> targets = resolveTargets(action, aliveEnemies);

            ActionResult result = action.execute(player, targets);
            for (DamageInstance dmg : result.getDamages()) {
                dmg.getTarget().takeDamage(dmg.getAmount());
            }

            applyActionResult(result);

            battleUI.displayActionResult(result);
            return action;
        }
    }

    private Action handleEnemyTurn(Enemy enemy) {
        Action action = enemyAI.chooseAction(enemy, context);

        List<Combatant> targets = List.of(player);

        ActionResult result = action.execute(enemy, targets);
        for (DamageInstance dmg : result.getDamages()) {
            dmg.getTarget().takeDamage(dmg.getAmount());
        }

        applyActionResult(result);

        battleUI.displayActionResult(result);
        return action;
    }

    private List<Combatant> resolveTargets(Action action, List<Enemy> aliveEnemies) {
        if (action instanceof ItemAction itemAction) {
            Item item = itemAction.getSelectedItem();

            if (item instanceof PowerStone) {
                Action skill = player.getSpecialSkill();

                // AOE → hit all enemies
                if (skill.isAOE()) {
                    return new ArrayList<>(aliveEnemies);
                }

                // Only one enemy → auto select
                if (aliveEnemies.size() == 1) {
                    return List.of(aliveEnemies.get(0));
                }

                // Multiple enemies → prompt
                int targetIdx = battleUI.promptTarget(aliveEnemies);
                return List.of(aliveEnemies.get(targetIdx));
            }
        }
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

    private void applyActionResult(ActionResult result) {

        // Apply effects
         for (StatusEffect effect : result.getEffects()) {
            effect.getTarget().addStatusEffect(effect);
        }
    }

    private boolean checkGameEnd() {
        return !player.isAlive() || context.getAliveEnemies().isEmpty();
    }

    private boolean handleBackupOrWin() {
        if (context.getAliveEnemies().isEmpty()) {
            if (level.hasBackup() && !level.isBackupSpawned()) {
                List<Enemy> backup = level.spawnBackupWave();
                context.setEnemies(level.getAllEnemies());
                battleUI.displayBackupSpawn(backup);
                return false;
            }
            return true;
        }
        return false;
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
