package entity;

import java.util.List;

// Interface for all executable actions under combatants
public interface Action {

    // Execute this action in the given battle context
    // Pass List as to handle future AOE effects if have any
    ActionResult execute(Combatant user, List<Combatant> targets); 
    
    // If any action has a cooldown, it will return a different cooldown
    default int getCooldownCost() {return 0;}
    
    boolean canExecute(Combatant user);
    
    boolean requiresTarget(); // sets up the boolean if need or not -> pass to battle engine

    // Returns the display name of this action.
    String getName();
}