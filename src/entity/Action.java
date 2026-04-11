package entity;

// Interface for all executable actions under combatants
public interface Action {
    // Execute this action in the given battle context
    ActionResult execute(BattleContext context);

    // Returns the display name of this action.
    String getName();
}