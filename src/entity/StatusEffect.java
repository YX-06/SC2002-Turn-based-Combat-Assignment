package entity;

// Represents a status effect applied to a combatant.
// Effects have a type, a stat modifier value, and a duration in rounds.
// ArcaneBlastBuff is permanent (does not decrement).
public class StatusEffect {
    private StatusEffectType type;
    private int duration;
    private int value;

    public StatusEffect(StatusEffectType type, int value, int duration) {
        this.type = type;
        this.value = value;
        this.duration = duration;
    }

    // Decrement the duration by 1. Permanent effects (ArcaneBlastBuff) are not decremented.
    public void tick() {
        if (type != StatusEffectType.ARCANE_BLAST_BUFF) {
            duration--;
        }
    }

    public boolean isExpired() {
        return duration <= 0 && type != StatusEffectType.ARCANE_BLAST_BUFF;
    }

    public StatusEffectType getType() { return type; }
    public int getDuration() { return duration; }
    public int getValue() { return value; }
    public void setDuration(int duration) { this.duration = duration; }
}
