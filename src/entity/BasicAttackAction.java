package entity;

// BasicAttack action: damage = max(0, attacker.atk - target.def).
// If target has SmokeBombEffect, damage = 0.
// Used by both players and enemies.
public class BasicAttackAction implements Action {
    private Combatant attacker;
    private Combatant target;
     

    public BasicAttackAction(Combatant attacker, Combatant target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public ActionResult execute(BattleContext context) {
        int damage = Math.max(0, attacker.getAtk() - target.getDef());
        
        int oldHp = target.getHp();

        String msg = attacker.getName() + " → BasicAttack → " + target.getName()
                + ": HP: " + oldHp + " → " + (oldHp - damage)
                + " (dmg: " + attacker.getAtk() + "-" + target.getDef() + "=" + damage + ")";

        if ((oldHp - damage) <= 0) {
            msg += " | ELIMINATED!";
        }

        ActionResult result = new ActionResult("BasicAttack", msg);

        // describe outcome only
        result.setDamage(damage, target);

        return result;
    }

    @Override
    public String getName() {
        return "BasicAttack";
    }
}
