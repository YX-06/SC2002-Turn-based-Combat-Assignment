package entity;

// Heals player by 100 HP, capped at maxHP.
// New HP = min(Current HP + 100, Max HP).
public class Potion extends Item {

    public Potion() {
        super("Potion");
    }

    @Override
    public ActionResult use(Player player, BattleContext context) {
        int oldHp = player.getHp();
        player.heal(100);
        int healed = player.getHp() - oldHp;
        String msg = player.getName() + " -> Item -> Potion used: HP: " + oldHp + " -> " + player.getHp() + " (+" + healed + ")";
        return new ActionResult("Potion", msg);
    }
}
