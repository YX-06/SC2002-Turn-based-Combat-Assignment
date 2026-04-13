package entity.action;

import entity.combat.Combatant;
import entity.effect.StunEffect;
import entity.result.ActionResult;
import java.util.List;

public class ShieldBash extends SpecialSkillAction {

    public ShieldBash() {
        super("Shield Bash", 3);
    }

    @Override
    public ActionResult execute(Combatant user, List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) {
            throw new IllegalArgumentException("Shield Bash requires one target.");
        }

        Combatant target = targets.get(0);

        int rawDamage = Math.max(0, user.getAtk() - target.getDef());
        int damage = target.modifyIncomingDamage(rawDamage);
        int oldHp = target.getHp();

        String msg = user.getName() + " -> Shield Bash -> " + target.getName()
                + ": HP " + oldHp + " -> " + Math.max(0, oldHp - damage)
                + " (dmg: " + damage + ") | Stun 2 turns";

        if ((oldHp - damage) <= 0) {
            msg += " | ELIMINATED!";
        }

        ActionResult result = new ActionResult(getName(), msg);
        result.addDamage(damage, target);
        if (target.isAlive()) {
            StunEffect stun = new StunEffect(2);
            result.addEffect(stun, target);
        }

        return result;
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }
}
