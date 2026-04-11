package entity;

import java.util.ArrayList;
import java.util.List;

// holds result of any action execution (damage dealt, effects applied, etc.)

public class ActionResult {
    private String actionName;
    private String message;

    private int damage;
    private Combatant target;

    private int cooldownChange;
    private List<StatusEffect> effects;

    public ActionResult(String actionName, String message) {
        this.actionName = actionName;
        this.message = message;
        this.effects = new ArrayList<>();
    }

    // DAMAGE
    public void setDamage(int damage, Combatant target) {
        this.damage = damage;
        this.target = target;
    }

    public int getDamage() { return damage; }
    public Combatant getTarget() { return target; }

    // EFFECTS
    public void addEffect(StatusEffect effect) {
        effects.add(effect);
    }

    // COOLDOWN
    public void setCooldownChange(int cooldownChange) {
        this.cooldownChange = cooldownChange;
    }

    public int getCooldownChange() { return cooldownChange; }

    // INFO
    public String getActionName() { return actionName; }
    public String getMessage() { return message; }
    
}