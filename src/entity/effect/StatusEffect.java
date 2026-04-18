package entity.effect;

import entity.combat.Combatant;

// Used as an abstract class based on OOP Principle LSP 
public abstract class StatusEffect {
    protected int duration; 

    StatusEffect(int duration) {
        this.duration = duration;
    }

    // called once whenever effefct is applied
    public abstract void onApply(Combatant target);
    
    // code logic on every turn on what it does
    public abstract void onTick(Combatant target);

    // any potential expire effect that may be done
    public abstract void onExpire(Combatant target);
    
    // Human-readable name shown in round-start status display; return null to suppress display
    public abstract String getDisplayName();

    public int getDuration() { return duration; }

    public int modifyIncomingDamage(int damage) {
        return damage; // default is no change -> for future effects may change
    }
    // for extensibility in future in case any other status effect may want to prevent action
    public boolean preventsAction() {
            return false;
        }

    public void reduceDuration() {
        duration--;
    } 
    
    public boolean isExpired() {
        return duration <= 0;
    }
}


