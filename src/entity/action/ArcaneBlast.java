package entity.action;

import entity.combat.Combatant;
import entity.effect.ArcaneBlastBuffEffect;
import entity.effect.StatusEffect;
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

            String dmgStr = String.valueOf(damage);
            if (rawDamage > 0 && damage == 0) {
                for (StatusEffect e : target.getStatusEffects()) {
                    if (e.modifyIncomingDamage(1) == 0 && e.getDisplayName() != null) {
                        dmgStr = "cannot damage due to " + e.getDisplayName();
                        break;
                    }
                }
            }

            sb.append("\n  ").append(target.getName())
            .append(": HP ").append(oldHp)
            .append(" -> ").append(Math.max(0, oldHp - damage))
            .append(" (dmg: ").append(dmgStr).append(")");

            if ((oldHp - damage) <= 0) {
                kills++;
                sb.append(" | ELIMINATED!");
            }
        }

        result.setMessage(sb.toString());

        if (kills > 0) {
            int atkBuff = kills * 10;
            ArcaneBlastBuffEffect buff = new ArcaneBlastBuffEffect(atkBuff);
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
