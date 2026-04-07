package entity;

// Warrior player class.
// Stats: HP 260, ATK 40, DEF 20, SPD 30.
// Special Skill: Shield Bash — deals BasicAttack damage to one target and stuns it for 2 turns.
public class Warrior extends Player {

    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public ActionResult executeSpecialSkill(Combatant target, BattleContext context) {
        int damage = Math.max(0, getAtk() - target.getDef());
        if (target.hasEffect(StatusEffectType.SMOKE_BOMB_EFFECT)) {
            damage = 0;
        }
        int oldHp = target.getHp();
        target.takeDamage(damage);
        target.addStatusEffect(new StatusEffect(StatusEffectType.STUN_EFFECT, 0, 2));

        String msg = getName() + " → Shield Bash → " + target.getName()
                + ": HP: " + oldHp + " → " + target.getHp()
                + " (dmg: " + getAtk() + "-" + target.getDef() + "=" + damage + ")"
                + " | " + target.getName() + " STUNNED (2 turns)";
        if (!target.isAlive()) {
            msg += " | ELIMINATED!";
        }
        return new ActionResult("Shield Bash", damage, msg, true);
    }

    @Override
    public String getSpecialSkillName() {
        return "Shield Bash";
    }
}
