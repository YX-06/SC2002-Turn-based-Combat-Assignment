package entity;

// Abstract item class. Items are single-use consumables chosen at game start.
// Subclassed by Potion, SmokeBomb, PowerStone.
public abstract class Item {
    protected String name;
    protected boolean consumed;

    public Item(String name) {
        this.name = name;
        this.consumed = false;
    }

    // Use this item in battle. Each subclass defines its own effect.
    public abstract ActionResult use(Player player, BattleContext context);

    public void markConsumed() {
        this.consumed = true;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public String getName() { return name; }
}