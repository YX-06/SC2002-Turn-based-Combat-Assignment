package entity;

import java.util.ArrayList;
import java.util.List;

// Abstract base class for all combatants (players and enemies).
// Manages HP, attack, defense, speed, and status effects.
public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHP;
    protected int atk;
    protected int def;
    protected int speed;
    protected List<StatusEffect> statusEffects;

    public Combatant(String name, int maxHP, int atk, int def, int speed) {
        this.name = name;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
    }

    public Combatant() {
    }

    public boolean isAlive() {
        return hp > 0;
    }

    // Reduce HP by the given damage amount. HP cannot go below 0.
    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    // Heal by the given amount, capped at maxHP.
    public void heal(int amount) {
        hp = Math.min(hp + amount, maxHP);
    }



    



    // Getters and setters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHP() { return maxHP; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public int getSpeed() { return speed; }
    public void setAtk(int atk) { this.atk = atk; }
    public void setDef(int def) { this.def = def; }
    public List<StatusEffect> getStatusEffects() { return statusEffects; }
}