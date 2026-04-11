package entity;

// Warrior player class.
// Stats: HP 260, ATK 40, DEF 20, SPD 30.
// Special Skill: Shield Bash — deals BasicAttack damage to one target and stuns it for 2 turns.
public class Warrior extends Player {

    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }
}
