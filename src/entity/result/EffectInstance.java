package entity.result;

import entity.combat.Combatant;
import entity.effect.StatusEffect;

public class EffectInstance {
    private StatusEffect effect;
    private Combatant target;

    public EffectInstance(StatusEffect effect, Combatant target) {
        this.effect = effect;
        this.target = target;
    }

    public StatusEffect getEffect() { return effect; }
    public Combatant getTarget() { return target; }
}
