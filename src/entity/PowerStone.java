package entity;

import java.util.List;

// Triggers the player's SpecialSkill as a free extra use.
// Does NOT start or change the cooldown timer.
public class PowerStone extends Item {
    private Combatant target;

    public PowerStone() {
        super("Power Stone");
    }

    // Set the target for targeted special skills (e.g. Warrior's Shield Bash).
    // Wizard's Arcane Blast ignores target (hits all enemies).
    public void setTarget(Combatant target) {
        this.target = target;
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

        result.setDamage(skillResult.getDamage(), skillResult.getTarget());

        for (StatusEffect effect : skillResult.getEffects()) {
            result.addEffect(effect);
        }

        return result;
    }
}
