package entity.item;

import entity.combat.Combatant;
import entity.combat.Player;
import entity.effect.SmokeBombEffect;
import entity.result.ActionResult;
import java.util.List;

// Grants SmokeBombEffect (duration=2): all incoming enemy attacks deal 0 damage
// for the current turn and the next turn.
public class SmokeBomb extends Item {

    public SmokeBomb() {
        super("Smoke Bomb");
    }

    @Override
    public ActionResult use(Player player, List<Combatant> targets)  {
        String msg = player.getName() + " → Item → Smoke Bomb used: Enemy attacks deal 0 damage this turn + next";
        SmokeBombEffect effect = new SmokeBombEffect(2);
        effect.setTarget(player);

        ActionResult result = new ActionResult("Smoke Bomb", msg);
        result.addEffect(effect);
        return result;
    }
}
