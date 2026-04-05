package entity;

import java.util.ArrayList;
import java.util.List;

// Abstract player class. Subclassed by Warrior and Wizard.
// Manages cooldown, inventory, and delegates special skill to subclasses.
public abstract class Player extends Combatant {
    protected int cooldown;
    protected List<Item> inventory;

    public Player(String name, int maxHP, int atk, int def, int speed) {
        super(name, maxHP, atk, def, speed);
        this.cooldown = 0;
        this.inventory = new ArrayList<>();
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
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

    // Execute this player's class-specific special skill.
    // Warrior: Shield Bash, Wizard: Arcane Blast.
    public abstract ActionResult executeSpecialSkill(Combatant target, BattleContext context);

    // Returns the display name of this player's special skill.
    public abstract String getSpecialSkillName();
}