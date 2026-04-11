package entity;

public class ArcaneBlastBuffEffect extends StatusEffect {

    private int amount;

    public ArcaneBlastBuffEffect(int amount) {
        super(-1); // not used
        this.amount = amount;
    }

    @Override
    public void onApply(Combatant target) {
        target.setAtk(target.getAtk() + amount);
    }

    @Override
    public void onTick(Combatant target) {
        // no turn logic
    }

    @Override
    public void onExpire(Combatant target) {
        // never expires and does nothing
    }

    @Override
    public boolean isExpired() {
        return false; // permanent buff
    }
}