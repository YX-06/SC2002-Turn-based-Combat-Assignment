package entity.action;

import entity.combat.Combatant;
import entity.combat.Player;

public abstract class SpecialSkillAction implements Action {
    protected String name;
    protected int cooldownRounds;

    public SpecialSkillAction(String name, int cooldownRounds) {
        this.name = name;
        this.cooldownRounds = cooldownRounds;
    }
    
    @Override
    public int getCooldownCost() {
        return cooldownRounds;
    }

    @Override
    public boolean canExecute(Combatant user) {
        if (!(user instanceof Player)) return false;
        return ((Player) user).getCooldown() == 0 && user.canAct();
    }

    @Override
    public String getName() {
        return name;
    }
}
