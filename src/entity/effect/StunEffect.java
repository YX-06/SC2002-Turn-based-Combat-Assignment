package entity.effect;

import entity.combat.Combatant;

public class StunEffect extends StatusEffect {

    public StunEffect(int duration) {
        super(duration);
    }

    @Override
    public String getDisplayName() { return "Stun"; }

    @Override
    public void onApply(Combatant target) {
        // nothing
    }

    @Override
    public void onTick(Combatant target) {
        //nothing
    }

    @Override
    public void onExpire(Combatant target) {
        // nothing occurs when it expires
    }

    @Override
    public boolean preventsAction() {
        return true;
    }
}
