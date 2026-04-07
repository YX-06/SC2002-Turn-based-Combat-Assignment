package entity;

// Defend action: applies DefendBuff (+10 DEF, duration=2) to the player.
// Increases defense for the current round and the next round.
public class DefendAction implements Action {
    private Player player;

    public DefendAction(Player player) {
        this.player = player;
    }

    @Override
    public ActionResult execute(BattleContext context) {
        StatusEffect defendBuff = new StatusEffect(StatusEffectType.DEFEND_BUFF, 10, 2);
        player.addStatusEffect(defendBuff);
        String msg = player.getName() + " → Defend: DEF +" + 10 + " for 2 turns (DEF: " + player.getDef() + ")";
        return new ActionResult("Defend", 0, msg);
    }

    @Override
    public String getName() { return "Defend"; }
}
