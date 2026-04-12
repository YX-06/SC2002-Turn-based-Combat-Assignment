package control;

import entity.*;
import entity.action.Action;
import entity.action.BasicAttackAction;
import entity.combat.Enemy;

// Default enemy strategy(not ai haha) — always chooses BasicAttack targeting the player.
public class BasicEnemyAI implements EnemyActionStrategy {

    @Override
    public Action chooseAction(Enemy enemy, BattleContext context) {
        return new BasicAttackAction();
    }
}
