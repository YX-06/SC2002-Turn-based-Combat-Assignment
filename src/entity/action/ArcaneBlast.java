package entity.action;

import entity.combat.Combatant;
import entity.effect.ArcaneBlastBuffEffect;
import entity.result.ActionResult;
import java.util.List;

public class ArcaneBlast extends SpecialSkillAction {

    public ArcaneBlast() {
        super("Arcane Blast", 3);
    }

    @Override
    
    public ActionResult execute(Combatant user, List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) {
            throw new IllegalArgumentException("Arcane Blast requires at least one target.");
        }

        int totalAtkBuff = 0;
        int currentAtk = user.getAtk();
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName()).append(" -> Arcane Blast:");

        ActionResult result = new ActionResult(getName(), "");

        for (Combatant target : targets) {
            if (!target.isAlive()) {
                continue;
            }

            int rawDamage = Math.max(0, currentAtk - target.getDef());
            int damage = target.modifyIncomingDamage(rawDamage);
            int oldHp = target.getHp();

            result.addDamage(damage, target);

            sb.append("\n  ").append(target.getName())
            .append(": HP ").append(oldHp)
            .append(" -> ").append(Math.max(0, oldHp - damage))
            .append(" (dmg: ").append(damage).append(")");

            if ((oldHp - damage) <= 0) {
                totalAtkBuff += 10;
                currentAtk += 10;
                sb.append(" | ELIMINATED!");
            }
        }

        result.setMessage(sb.toString());

        if (totalAtkBuff > 0) {
            ArcaneBlastBuffEffect buff = new ArcaneBlastBuffEffect(totalAtkBuff);
            result.addEffect(buff, user);
        }

        return result;
    }
    
    @Override
    public boolean isAOE() {
        return true;
    }
    @Override
    public boolean requiresTarget() {
        return false;
    }
}
