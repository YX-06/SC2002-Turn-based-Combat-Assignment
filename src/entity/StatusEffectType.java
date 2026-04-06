package entity;

// Enum of all status effect types in the game.
public enum StatusEffectType {
    DEFEND_BUFF,        // +10 DEF, duration=2
    STUN_EFFECT,        // target skips turns, duration=2
    SMOKE_BOMB_EFFECT,  // all incoming damage = 0, duration=2
    ARCANE_BLAST_BUFF   // +10 ATK per kill, permanent for level
}