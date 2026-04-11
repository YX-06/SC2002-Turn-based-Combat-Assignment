package entity.combat;

import entity.action.Action;
import entity.effect.StatusEffect;
import java.util.ArrayList;
import java.util.Iterator;
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
    protected List<Action> actions;

    public Combatant(String name, int maxHP, int atk, int def, int speed) {
        this.name = name;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public Combatant() {
    }

    public boolean isAlive() {
        return hp > 0;
    }

    // DAMAGE (Cannot go below 0)
    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    // HEAL (Capped at maxHP)
    public void heal(int amount) {
        hp = Math.min(hp + amount, maxHP);
    }

    // STATUS EFFECTS
    public void addStatusEffect(StatusEffect effect) {
        effect.onApply(this);
        statusEffects.add(effect);
    }

    public void tickEffects() {
        Iterator<StatusEffect> it = statusEffects.iterator();

        while (it.hasNext()) {
            StatusEffect effect = it.next();
            effect.onTick(this);
            effect.reduceDuration();

            if (effect.isExpired()) {
                effect.onExpire(this);
                it.remove();
            }
        }
    }

    
    public boolean canAct() {
        for (StatusEffect effect : statusEffects) {
            if (effect.preventsAction()) {
                return false;
            }
        }
        return true;
    }
    
    // based on its status effects, checks if the damage its taking in will change
    public int modifyIncomingDamage(int damage) {
        int modified = damage;

        for (StatusEffect effect : statusEffects) {
            modified = effect.modifyIncomingDamage(modified);
        }

        return modified;
    }

    
    // ACTIONS
    public List<Action> getActions() {
        return actions;
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
