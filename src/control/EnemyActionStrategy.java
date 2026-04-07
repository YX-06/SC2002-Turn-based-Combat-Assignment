package control;

import entity.Action;
import entity.Enemy;
import entity.BattleContext;

// Strategy interface for enemy AI decision-making.
// Extensible: new enemy behaviours can be added without modifying BattleEngine.
public interface EnemyActionStrategy {
    // Choose an action for the given enemy in the current battle context.
    Action chooseAction(Enemy enemy, BattleContext context);
}
