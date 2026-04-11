package entity.item;

import entity.combat.Combatant;
import entity.combat.Player;
import entity.result.ActionResult;
import java.util.List;

// items are single use

public abstract class Item {
    protected String name;
    protected boolean consumed;

    public Item(String name) {
        this.name = name;
        this.consumed = false;
    }

    // use the item in battle with each subclass having a different result
    public abstract ActionResult use(Player player, List<Combatant> targets);

    public boolean requiresTarget() {
        return false;
    }
    
    public void markConsumed() {
        this.consumed = true;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public String getName() { return name; }
}
