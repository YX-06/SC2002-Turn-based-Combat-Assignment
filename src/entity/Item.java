package entity;

// items are single use

public abstract class Item {
    protected String name;
    protected boolean consumed;

    public Item(String name) {
        this.name = name;
        this.consumed = false;
    }

    // use the item in battle with each subclass having a different result
    public abstract ActionResult use(Player player, BattleContext context);

    public void markConsumed() {
        this.consumed = true;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public String getName() { return name; }
}