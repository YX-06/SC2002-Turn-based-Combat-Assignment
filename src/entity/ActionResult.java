package entity;

// Holds the result of an action execution (damage dealt, effects applied, etc.).
public class ActionResult {
    private String actionName;
    private int damageDealt;
    private String message;
    private boolean usedSpecialSkill;

    public ActionResult(String actionName, int damageDealt, String message) {
        this(actionName, damageDealt, message, false);
    }

    public ActionResult(String actionName, int damageDealt, String message, boolean usedSpecialSkill) {
        this.actionName = actionName;
        this.damageDealt = damageDealt;
        this.message = message;
        this.usedSpecialSkill = usedSpecialSkill;
    }

    public String getActionName() { return actionName; }
    public int getDamageDealt() { return damageDealt; }
    public String getMessage() { return message; }
    public boolean isUsedSpecialSkill() { return usedSpecialSkill; }
}