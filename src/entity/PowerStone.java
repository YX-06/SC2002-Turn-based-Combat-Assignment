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

}
