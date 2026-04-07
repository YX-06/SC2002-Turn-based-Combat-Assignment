package control;

import entity.*;

// Default enemy strategy(not ai haha) — always chooses BasicAttack targeting the player.
public class BasicEnemyAI implements EnemyActionStrategy {

    @Override
    public Action chooseAction(Enemy enemy, BattleContext context) {
        return new BasicAttackAction(enemy, context.getPlayer());
    }
}
