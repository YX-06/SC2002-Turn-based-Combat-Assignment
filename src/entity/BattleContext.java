package entity;

import entity.combat.Enemy;
import entity.combat.Player;
import java.util.ArrayList;
import java.util.List;

// Snapshot of the current battle state, passed to actions and items that need context.
public class BattleContext {
    private Player player;
    private List<Enemy> enemies;

    public BattleContext(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
    }

    public Player getPlayer() { return player; }
    public List<Enemy> getEnemies() { return enemies; }

    // Returns only the alive enemies from the current enemy list.
    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.isAlive()) alive.add(e);
        }
        return alive;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
}
