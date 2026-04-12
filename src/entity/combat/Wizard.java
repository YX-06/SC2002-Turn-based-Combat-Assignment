package entity.combat;

import entity.action.ArcaneBlast;
import entity.action.BasicAttackAction;
import entity.action.DefendAction;
import entity.action.ItemAction;

public class Wizard extends Player {

    public Wizard(String name, int maxHP, int atk, int def, int speed) {
        super(name, maxHP, atk, def, speed);
        
        // Basic actions
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        actions.add(new ItemAction());
        
        // Special skill
        this.specialSkill = new ArcaneBlast();
        actions.add(specialSkill);
    }
}
