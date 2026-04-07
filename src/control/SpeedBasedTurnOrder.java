package control;

import entity.Combatant;
import java.util.ArrayList;
import java.util.List;

// Default turn order strategy: higher speed goes first.
public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);
        ordered.sort((a, b) -> b.getSpeed() - a.getSpeed());
        return ordered;
    }
}
