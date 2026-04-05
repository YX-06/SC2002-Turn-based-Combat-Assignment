package entity;

// Interface for combat actions (OCP: new actions can be added without modifying BattleEngine).
// Implemented by BasicAttackAction, DefendAction, etc.
public interface Action {
    // Execute this action in the given battle context.
    ActionResult execute(BattleContext context);

    // Returns the display name of this action.
    String getName();
}