package entity.action;

import entity.combat.Combatant;
import entity.result.ActionResult;
import java.util.List;

// BasicAttack action: damage = max(0, attacker.atk - target.def)
// utilised by both player and combatant
public class BasicAttackAction implements Action {

    @Override
    public ActionResult execute(Combatant user, List<Combatant> targets) {

        if (targets.isEmpty()) {
            throw new IllegalArgumentException("BasicAttack requires a target");
        }

        Combatant target = targets.get(0);

        int damage = Math.max(0, user.getAtk() - target.getDef());
        int oldHp = target.getHp();

        String msg = user.getName() + " → BasicAttack → " + target.getName()
                + ": HP: " + oldHp + " → " + Math.max(0, oldHp - damage)
                + " (dmg: " + user.getAtk() + "-" + target.getDef() + "=" + damage + ")";

        if ((oldHp - damage) <= 0) {
            msg += " | ELIMINATED!";
        }

        ActionResult result = new ActionResult("BasicAttack", msg);

        result.setDamage(damage, target);

        return result;
    }

    @Override
    public boolean canExecute(Combatant user) {
        return true;
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public String getName() {
        return "BasicAttack";
    }
}
