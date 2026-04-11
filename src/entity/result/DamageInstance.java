package entity.result;

import entity.combat.Combatant;

public class DamageInstance {
    private int amount;
    private Combatant target;

    public DamageInstance(int amount, Combatant target) {
        this.amount = amount;
        this.target = target;
    }

    public int getAmount() { return amount; }
    public Combatant getTarget() { return target; }
}
