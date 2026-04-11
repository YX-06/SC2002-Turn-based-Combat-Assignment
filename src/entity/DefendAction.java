package entity;
import java.util.List;

// Defend action: applies DefendBuff (+10 DEF, duration=2) to the player.
// Increases defense for the current round and the next round.
public class DefendAction implements Action {
    @Override
    public ActionResult execute(Combatant user, List<Combatant> targets) {
        StatusEffect defendBuff = new DefendBuffEffect(2, 10);

        String msg = user.getName() + " → Defend: DEF +10 for 2 turns";

        ActionResult result = new ActionResult("Defend", msg);

        // added effects to the ActionResult 
        result.addEffect(defendBuff);

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