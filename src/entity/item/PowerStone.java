package entity.item;

import entity.action.SpecialSkillAction;
import entity.combat.Combatant;
import entity.combat.Player;
import entity.result.ActionResult;
import entity.result.DamageInstance;
import entity.result.EffectInstance;
import java.util.List;

// Triggers the player's SpecialSkill as a free extra use.
// Does NOT start or change the cooldown timer.
public class PowerStone extends Item {

    public PowerStone() {
        super("Power Stone");
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public ActionResult use(Player player, List<Combatant> targets) {
        SpecialSkillAction specialSkill = player.getSpecialSkill();

        if (specialSkill == null) {
            return new ActionResult("Power Stone", "Power Stone failed: no special skill found.");
        }

        ActionResult skillResult = specialSkill.execute(player, targets);

        ActionResult result = new ActionResult(
                "Power Stone",
                player.getName() + " → Power Stone used!\n" + skillResult.getMessage()
        );

        for (DamageInstance dmg : skillResult.getDamages()) {
            result.addDamage(dmg.getAmount(), dmg.getTarget());
        }

        for (EffectInstance effect : skillResult.getEffects()) {
            result.addEffect(effect.getEffect(), effect.getTarget());
        }

        return result;
    }
}
