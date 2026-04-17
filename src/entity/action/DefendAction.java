package entity.action;

import entity.combat.Combatant;
import entity.effect.DefendBuffEffect;
import entity.effect.StatusEffect;
import entity.result.ActionResult;
import java.util.List;

// defend action: applies DefendBuff (+10 DEF, duration=2) to player
// increase defense for the current round and the next round
public class DefendAction implements Action {
    @Override
    public ActionResult execute(Combatant user, List<Combatant> targets) {
        String msg = user.getName() + " → Defend: DEF +10 for 2 turns";
        StatusEffect defendBuff = new DefendBuffEffect(2, 10);
        
        ActionResult result = new ActionResult("Defend", msg);
        
        result.addEffect(defendBuff, user);

    

        return result;
    }

    @Override
    public boolean canExecute(Combatant user) {
        return true;
    }

    @Override
    public boolean requiresTarget() {
        return false; 
    }

    @Override
    public String getName() {
        return "Defend";
    }
}
