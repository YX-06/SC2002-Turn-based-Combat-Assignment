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

        int kills = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName()).append(" -> Arcane Blast:");

        ActionResult result = new ActionResult(getName(), "");

        for (Combatant target : targets) {
            if (!target.isAlive()) {
                continue;
            }

            int rawDamage = Math.max(0, user.getAtk() - target.getDef());
            int damage = target.modifyIncomingDamage(rawDamage);
            int oldHp = target.getHp();

            result.addDamage(damage, target);

            sb.append("\n  ").append(target.getName())
            .append(": HP ").append(oldHp)
            .append(" -> ").append(Math.max(0, oldHp - damage))
            .append(" (dmg: ").append(damage).append(")");

            if ((oldHp - damage) <= 0) {
                kills++;
                sb.append(" | ELIMINATED!");
            }
        }

        result.setMessage(sb.toString());

        if (kills > 0) {
            int atkBuff = kills * 10;
            ArcaneBlastBuffEffect buff = new ArcaneBlastBuffEffect(atkBuff);
            buff.setTarget(user);
            result.addEffect(buff);
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
