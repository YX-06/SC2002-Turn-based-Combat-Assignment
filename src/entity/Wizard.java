package entity;

public class Wizard extends Player {

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
        
        // Basic actions
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        actions.add(new ItemAction());
        
        // Special skill
        this.specialSkill = new ShieldBash();
        actions.add(specialSkill);
    }
}