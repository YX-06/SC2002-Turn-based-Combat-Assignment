package entity;

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
    public ActionResult use(Player player, BattleContext context) {
        ActionResult skillResult = player.executeSpecialSkill(target, context);
        String msg = player.getName() + " → Item → Power Stone used → "
                + player.getSpecialSkillName() + " triggered!\n" + skillResult.getMessage()
                + "\n  Cooldown unchanged (Power Stone does not affect cooldown) | Power Stone consumed";
        return new ActionResult("Power Stone", skillResult.getDamageDealt(), msg, true);
    }
}
