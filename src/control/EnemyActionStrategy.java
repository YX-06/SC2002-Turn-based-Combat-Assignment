package control;

import entity.BattleContext;
import entity.action.Action;
import entity.combat.Enemy;

// Strategy interface for enemy AI decision-making.
// Extensible: new enemy behaviours can be added without modifying BattleEngine.
public interface EnemyActionStrategy {
    // Choose an action for the given enemy in the current battle context.
    Action chooseAction(Enemy enemy, BattleContext context);
}
