package entity;

// Grants SmokeBombEffect (duration=2): all incoming enemy attacks deal 0 damage
// for the current turn and the next turn.
public class SmokeBomb extends Item {

    public SmokeBomb() {
        super("Smoke Bomb");
    }

    @Override
    public ActionResult use(Player player, BattleContext context) {
        player.addStatusEffect(new StatusEffect(StatusEffectType.SMOKE_BOMB_EFFECT, 0, 2));
        String msg = player.getName() + " → Item → Smoke Bomb used: Enemy attacks deal 0 damage this turn + next";
        return new ActionResult("Smoke Bomb", 0, msg);
    }
}
