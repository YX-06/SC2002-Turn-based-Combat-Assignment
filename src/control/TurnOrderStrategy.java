package control;

import entity.combat.Combatant;
import java.util.List;

// Strategy interface for determining turn order each round.
// Extensible: new turn-order mechanisms can be added without modifying BattleEngine.
public interface TurnOrderStrategy {
    // Given a list of alive combatants, return them sorted by turn priority.
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
