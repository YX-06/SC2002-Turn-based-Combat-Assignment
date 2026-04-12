package entity.item;

import entity.combat.Combatant;
import entity.combat.Player;
import entity.result.ActionResult;
import java.util.List;

// Heals player by 100 HP, capped at maxHP.
// New HP = min(Current HP + 100, Max HP).
public class Potion extends Item {

    public Potion() {
        super("Potion");
    }

    @Override
    public ActionResult use(Player player, List<Combatant> targets)  {
        int oldHp = player.getHp();
        player.heal(100);
        int healed = player.getHp() - oldHp;
        String msg = player.getName() + " -> Item -> Potion used: HP: " + oldHp + " -> " + player.getHp() + " (+" + healed + ")";
        return new ActionResult("Potion", msg);
    }
}
