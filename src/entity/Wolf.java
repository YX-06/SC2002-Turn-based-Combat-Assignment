package entity;

// Wolf enemy type.
// Stats: HP 40, ATK 45, DEF 5, SPD 35.
public class Wolf extends Enemy {

    public Wolf(String name) {
        super(name, 40, 45, 5, 35);
    }
}