package entity.effect;

import entity.combat.Combatant;

public class SmokeBombEffect extends StatusEffect {

    public SmokeBombEffect(int duration) {
        super(duration);
    }

    @Override
    public String getDisplayName() { return "Smoke Bomb"; }

    @Override
    public void onApply(Combatant target) {
        // nothing
    }

    @Override
    public void onTick(Combatant target) {
        // nothing per turn
    }

    @Override
    public void onExpire(Combatant target) {
        // nothing to clean up
    }

    @Override
    public int modifyIncomingDamage(int damage) {
        return 0; // completely get rid of damage
    }
}
