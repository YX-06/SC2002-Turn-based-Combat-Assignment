package control;

import entity.BattleContext;
import entity.action.Action;
import entity.combat.Enemy;

// strategy interface for enemy strategy to choose
// extensible and returns action
public interface EnemyActionStrategy {
    // choose specific action for the character
    Action chooseAction(Enemy enemy, BattleContext context);
}
