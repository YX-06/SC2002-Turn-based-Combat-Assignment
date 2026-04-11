package entity.combat;

import entity.action.ArcaneBlast;
import entity.action.BasicAttackAction;
import entity.action.DefendAction;
import entity.action.ItemAction;

public class Wizard extends Player {

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
        
        // Basic actions
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        actions.add(new ItemAction());
        
        // Special skill
        this.specialSkill = new ArcaneBlast();
        actions.add(specialSkill);
    }
}
