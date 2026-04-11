package entity;

import entity.combat.Enemy;
import entity.combat.Goblin;
import entity.combat.Wolf;
import java.util.ArrayList;
import java.util.List;

// Represents a difficulty level and manages enemy waves (initial + backup).
// Easy:   3 Goblins, no backup.
// Medium: 1 Goblin + 1 Wolf, backup 2 Wolves.
// Hard:   2 Goblins, backup 1 Goblin + 2 Wolves.
public class Level {
    private int levelNo;
    private String difficulty;
    private List<Enemy> allEnemies;
    private List<Enemy> initialEnemies;
    private boolean backupSpawned;
    private boolean hasBackup;

    public Level(int levelNo, String difficulty) {
        this.levelNo = levelNo;
        this.difficulty = difficulty;
        this.allEnemies = new ArrayList<>();
        this.initialEnemies = new ArrayList<>();
        this.backupSpawned = false;
        this.hasBackup = (levelNo >= 2);
        spawnInitialWave();
    }

    private void spawnInitialWave() {
        switch (levelNo) {
            case 1: // Easy: 3 Goblins
                initialEnemies.add(new Goblin("Goblin A"));
                initialEnemies.add(new Goblin("Goblin B"));
                initialEnemies.add(new Goblin("Goblin C"));
                break;
            case 2: // Medium: 1 Goblin + 1 Wolf
                initialEnemies.add(new Goblin("Goblin"));
                initialEnemies.add(new Wolf("Wolf"));
                break;
            case 3: // Hard: 2 Goblins
                initialEnemies.add(new Goblin("Goblin A"));
                initialEnemies.add(new Goblin("Goblin B"));
                break;
        }
        allEnemies.addAll(initialEnemies);
    }

    // Spawn the backup wave of enemies. Only called once after the initial wave is defeated.
    // Returns the newly spawned enemies.
    public List<Enemy> spawnBackupWave() {
        if (backupSpawned || !hasBackup) return new ArrayList<>();
        backupSpawned = true;
        List<Enemy> backup = new ArrayList<>();

        switch (levelNo) {
            case 2: // Medium backup: 2 Wolves
                backup.add(new Wolf("Wolf A"));
                backup.add(new Wolf("Wolf B"));
                break;
            case 3: // Hard backup: 1 Goblin + 2 Wolves
                backup.add(new Goblin("Goblin C"));
                backup.add(new Wolf("Wolf A"));
                backup.add(new Wolf("Wolf B"));
                break;
        }

        allEnemies.addAll(backup);
        return backup;
    }

    // Check if ALL enemies (initial + backup) are defeated.
    public boolean allEnemiesDefeated() {
        for (Enemy e : allEnemies) {
            if (e.isAlive()) return false;
        }
        return true;
    }

    // Get alive enemies from the full enemy list.
    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy e : allEnemies) {
            if (e.isAlive()) alive.add(e);
        }
        return alive;
    }

    public boolean isBackupSpawned() { return backupSpawned; }
    public boolean hasBackup() { return hasBackup; }
    public String getDifficulty() { return difficulty; }
    public int getLevelNo() { return levelNo; }
    public List<Enemy> getAllEnemies() { return allEnemies; }
}
