package entity;

public class DefendBuffEffect extends StatusEffect {

    private int amount;

    public DefendBuffEffect(int duration, int amount) {
        super(duration);
        this.amount = amount;
    }

    @Override
    public void onApply(Combatant target) {
        target.setDef(target.getDef() + amount);
    }

    @Override
    public void onTick(Combatant target) {
        // nothing occurs
    }

    @Override
    public void onExpire(Combatant target) {
        target.setDef(target.getDef() - amount);
    }
}