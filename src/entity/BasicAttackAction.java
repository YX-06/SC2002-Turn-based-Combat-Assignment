package entity;

// BasicAttack action: damage = max(0, attacker.atk - target.def).
// If target has SmokeBombEffect, damage = 0.
// Used by both players and enemies.
public class BasicAttackAction implements Action {
    private Combatant attacker;
    private Combatant target;

    public BasicAttackAction(Combatant attacker, Combatant target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public ActionResult execute(BattleContext context) {
        int damage = Math.max(0, attacker.getAtk() - target.getDef());
        if (target.hasEffect(StatusEffectType.SMOKE_BOMB_EFFECT)) {
            damage = 0;
        }
        int oldHp = target.getHp();
        target.takeDamage(damage);

        StringBuilder msg = new StringBuilder();
        msg.append(attacker.getName()).append(" → BasicAttack → ").append(target.getName());
        msg.append(": HP: ").append(oldHp).append(" → ").append(target.getHp());
        msg.append(" (dmg: ").append(attacker.getAtk()).append("-").append(target.getDef()).append("=").append(damage).append(")");

        if (target.hasEffect(StatusEffectType.SMOKE_BOMB_EFFECT) && damage == 0) {
            msg.append(" (Smoke Bomb active)");
        }
        if (!target.isAlive()) {
            msg.append(" | ELIMINATED!");
        }

        return new ActionResult("BasicAttack", damage, msg.toString());
    }

    @Override
    public String getName() { return "BasicAttack"; }
}
