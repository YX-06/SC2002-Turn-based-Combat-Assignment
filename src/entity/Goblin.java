package entity;

// Goblin enemy type.
// Stats: HP 55, ATK 35, DEF 15, SPD 25.
public class Goblin extends Enemy {

    public Goblin(String name) {
        super(name, 55, 35, 15, 25);
    }
}