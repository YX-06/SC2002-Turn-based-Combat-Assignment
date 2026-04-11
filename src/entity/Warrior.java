package entity;

// Warrior player class.
// Stats: HP 260, ATK 40, DEF 20, SPD 30.
// Special Skill: Shield Bash - deals BasicAttack damage to one target and stuns it for 2 turns.
public class Warrior extends Player {

    public Warrior(String name, int maxHP, int atk, int def, int speed) {
        super(name, maxHP, atk, def, speed);

        this.specialSkill = new ShieldBashAction();
        
        // Basic actions
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        actions.add(new ItemAction());
        actions.add(specialSkill);

        // Special skill
        this.specialSkill = new ShieldBashAction();
        actions.add(specialSkill);
    }
}
