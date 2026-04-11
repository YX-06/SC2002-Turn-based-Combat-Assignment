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
            if (!target.isAlive()) {continue;}

            int rawDamage = Math.max(0, user.getAtk() - target.getDef());
            int damage = target.modifyIncomingDamage(rawDamage);
            int oldHp = target.getHp();
            
            target.takeDamage(damage);
            totalDamage += damage;

            sb.append("\n  ").append(target.getName())
              .append(": HP ").append(oldHp)
              .append(" -> ").append(target.getHp())
              .append(" (dmg: ").append(damage).append(")");

            if (!target.isAlive()) { // checks if it killed that specific target
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
