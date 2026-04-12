package entity.result;

import entity.combat.Combatant;
import entity.effect.StatusEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// holds result of any action execution (damage dealt, effects applied, etc.)

public class ActionResult {
    private String actionName;
    private String message;

    private List<EffectInstance> effects;
    private List<DamageInstance> damages = new ArrayList<>(); // Used to see Damage + Target

    public ActionResult(String actionName, String message) {
        this.actionName = actionName;       
        this.message = message;
        this.effects = new ArrayList<>();
    }

    // DAMAGE
    public void addDamage(int amount, Combatant target) {
        damages.add(new DamageInstance(amount, target));
    }

    public List<DamageInstance> getDamages() {
        return Collections.unmodifiableList(damages);
    }

    // EFFECTS
    public void addEffect(StatusEffect effect, Combatant target) {
        effects.add(new EffectInstance(effect, target));
    }

    public List<EffectInstance> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    // SET INFO
    public void setMessage(String message) {
        this.message = message;
    }

    // INFO
    public String getActionName() { return actionName; }
    public String getMessage() { return message; }
    
}
