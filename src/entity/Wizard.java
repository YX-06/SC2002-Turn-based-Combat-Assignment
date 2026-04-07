package entity;

import java.util.List;

// Wizard player class.
// Stats: HP 200, ATK 50, DEF 10, SPD 20.
// Special Skill: Arcane Blast — deals BasicAttack damage to ALL alive enemies.
// Each enemy killed by Arcane Blast adds +10 ATK (permanent for the level).
public class Wizard extends Player {

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    @Override
    public ActionResult executeSpecialSkill(Combatant target, BattleContext context) {
        List<Enemy> enemies = context.getAliveEnemies();
        int startAtk = getAtk();
        int totalDamage = 0;
        int kills = 0;
        StringBuilder msg = new StringBuilder();

        msg.append(getName()).append(" → Arcane Blast → All Enemies (ATK: ").append(startAtk).append("):\n");

        for (Enemy enemy : enemies) {
            int damage = Math.max(0, startAtk - enemy.getDef());
            int oldHp = enemy.getHp();
            enemy.takeDamage(damage);
            totalDamage += damage;

            msg.append("  ").append(enemy.getName())
               .append(": HP: ").append(oldHp).append(" → ").append(enemy.getHp())
               .append(" (dmg: ").append(startAtk).append("-").append(enemy.getDef()).append("=").append(damage).append(")");

            if (!enemy.isAlive()) {
                kills++;
                msg.append(" | ELIMINATED!");
            }
            msg.append("\n");
        }

        // Apply ATK bonus after all damage is dealt
        if (kills > 0) {
            int atkBefore = getAtk();
            for (int i = 0; i < kills; i++) {
                addStatusEffect(new StatusEffect(StatusEffectType.ARCANE_BLAST_BUFF, 10, Integer.MAX_VALUE));
            }
            msg.append("  ATK: ").append(atkBefore).append(" → ").append(getAtk())
               .append(" (+").append(kills * 10).append(" from ").append(kills).append(" kill(s))");
        }

        return new ActionResult("Arcane Blast", totalDamage, msg.toString().trim(), true);
    }

    @Override
    public String getSpecialSkillName() {
        return "Arcane Blast";
    }
}
