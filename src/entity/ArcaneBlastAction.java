package entity;

import java.util.List;

public class ArcaneBlastAction extends SpecialSkillAction {

    public ArcaneBlastAction() {
        super("Arcane Blast", 3);
    }

    @Override
    public ActionResult execute(Combatant user, List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) {
            throw new IllegalArgumentException("Arcane Blast requires at least one target.");
        }

        int totalDamage = 0;
        int kills = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName()).append(" -> Arcane Blast:");

        for (Combatant target : targets) {
            int rawDamage = Math.max(0, user.getAtk() - target.getDef());
            int damage = target.modifyIncomingDamage(rawDamage);
            int oldHp = target.getHp();

            totalDamage += damage;

            sb.append("\n  ").append(target.getName())
              .append(": HP ").append(oldHp)
              .append(" -> ").append(Math.max(0, oldHp - damage))
              .append(" (dmg: ").append(damage).append(")");

            if (oldHp - damage <= 0) {
                kills++;
                sb.append(" | ELIMINATED!");
            }
        }

        ActionResult result = new ActionResult(getName(), sb.toString());
        result.setDamage(totalDamage, null);

        if (kills > 0) {
            int atkBuff = kills * 10;
            result.addEffect(new ArcaneBlastBuffEffect(atkBuff)); // engine can apply to user
        }

        return result;
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }
}
