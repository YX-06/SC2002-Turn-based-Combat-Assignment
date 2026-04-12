package control;

import entity.*;
import entity.combat.Player;
import entity.combat.Warrior;
import entity.combat.Wizard;
import entity.item.Item;
import entity.item.Potion;
import entity.item.PowerStone;
import entity.item.SmokeBomb;

// Handles game initialisation: creates player, items, and level.
public class GameSetupController {

    // Create a player based on the class choice.
    // 1 = Warrior, 2 = Wizard.
    public Player createPlayer(int classChoice) {
        switch (classChoice) {
            case 1: return new Warrior("Warrior", 260, 40, 20, 30);
            case 2: return new Wizard("Wizard", 200, 50, 10, 20);
            default: throw new IllegalArgumentException("Invalid class choice: " + classChoice);
        }
    }

    // Create an item based on the item choice.
    // 1 = Potion, 2 = Smoke Bomb, 3 = Power Stone.
    public Item createItem(int itemChoice) {
        switch (itemChoice) {
            case 1: return new Potion();
            case 2: return new SmokeBomb();
            case 3: return new PowerStone();
            default: return new Potion();
        }
    }

    // Create a level based on the difficulty choice.
    // 1 = Easy, 2 = Medium, 3 = Hard.
    public Level createLevel(int difficulty) {
        String[] names = {"Easy", "Medium", "Hard"};
        return new Level(difficulty, names[difficulty - 1]);
    }
}
