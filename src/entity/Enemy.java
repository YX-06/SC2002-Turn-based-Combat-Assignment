package entity;

// Abstract enemy class. Subclassed by Goblin and Wolf.
// Enemies always perform BasicAttack on their turn.
public abstract class Enemy extends Combatant {

    public Enemy(String name, int maxHP, int atk, int def, int speed) {
        super(name, maxHP, atk, def, speed);
    }
}