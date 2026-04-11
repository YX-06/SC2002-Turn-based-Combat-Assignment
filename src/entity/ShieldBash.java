package entity;

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
        target.takeDamage(damage);

        String msg = user.getName() + " → Shield Bash → " + target.getName()
                + ": HP " + oldHp + " → " + target.getHp()
                + " (dmg: " + damage + ") | Stun 2 turns";

        if (!target.isAlive()) {
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
