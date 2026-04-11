package entity;

import java.util.List;

public class ShieldBashAction extends SpecialSkillAction {

    public ShieldBashAction() {
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

        if (oldHp - damage <= 0) {
            msg += " | ELIMINATED!";
        }

        ActionResult result = new ActionResult(getName(), msg);
        result.setDamage(damage, target);
        result.addEffect(new StunEffect(2)); // engine can apply this to the target

        return result;
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }
}
