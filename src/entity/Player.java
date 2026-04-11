package entity;

import java.util.ArrayList;
import java.util.List;

// Abstract player class. Subclassed by Warrior and Wizard.
// Manages cooldown, inventory, and delegates special skill to subclasses.
public abstract class Player extends Combatant {
    protected int cooldown;
    protected List<Item> inventory;
    protected SpecialSkillAction specialSkill;
    
    public Player(String name, int maxHP, int atk, int def, int speed) {
        super(name, maxHP, atk, def, speed);
        this.cooldown = 0;
        this.inventory = new ArrayList<>();
    }

    // Applying cooldown
    public void applyCooldown(int cost) {
        if (cost > 0) {
            this.cooldown = cooldown; 
        }
        else if (this.cooldown > 0) {
            this.cooldown--;
        }
        
    }

    public void decrementCooldown() {
        if (cooldown > 0) cooldown--;
    }

    public int getCooldown() { return cooldown; }

    public boolean canUseSpecialSkill() {
        return cooldown == 0;
    }

    public List<Item> getInventory() { return inventory; }

    public void addItem(Item item) {
        inventory.add(item);
    }

    // Returns a list of items that have not been consumed.
    public List<Item> getUsableItems() {
        List<Item> usable = new ArrayList<>();
        for (Item item : inventory) {
            if (!item.isConsumed()) usable.add(item);
        }
        return usable;
    }

    public boolean hasUsableItems() {
        for (Item item : inventory) {
            if (!item.isConsumed()) return true;
        }
        return false;
    }

    // Returns the display name of this player's special skill.
    public SpecialSkillAction getSpecialSkill() {
        return specialSkill;
    }
}
