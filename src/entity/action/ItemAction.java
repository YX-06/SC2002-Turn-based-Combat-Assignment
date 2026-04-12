package entity.action;

import entity.combat.Combatant;
import entity.combat.Player;
import entity.item.Item;
import entity.result.ActionResult;
import java.util.List;

public class ItemAction implements Action {
    private Item selectedItem;

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    @Override
    public ActionResult execute(Combatant user, List<Combatant> targets) {
        if (!(user instanceof Player player)) {
            throw new IllegalArgumentException("Only players can use items.");
        }

        if (selectedItem == null) {
            throw new IllegalStateException("No item selected.");
        }

        ActionResult result = selectedItem.use(player, targets);
        selectedItem.markConsumed();
        return result;
    }

    @Override
    public boolean canExecute(Combatant user) {
        return user instanceof Player
                && selectedItem != null
                && !selectedItem.isConsumed();
    }

    @Override
    public boolean requiresTarget() {
        return selectedItem != null && selectedItem.requiresTarget();
    }

    @Override
    public int getCooldownCost() {
        return 0;
    }

    @Override
    public String getName() {
        return selectedItem.getName();
    }
}
