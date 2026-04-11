package entity;

public class Wizard extends Player {

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }


    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }
}
