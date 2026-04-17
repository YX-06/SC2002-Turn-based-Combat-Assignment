package control;

import entity.combat.Combatant;
import java.util.List;

// interface to choose and determine order of combatants
// extensible and returns list
public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
