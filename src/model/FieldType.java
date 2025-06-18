package model;

/**
 * Jedes Feld ist von einem bestimmten Typ. Ausserdem schreiben wir hier mit, ob man sich "da rein" bewegen kann.
 */
public enum FieldType {
    /** Wand - nicht begehbar */
    WALL,
    /** Gang - begehbar */
    PATH,
    /** Startfeld */
    START,
    /** Zielfeld */
    GOAL;
    
    /**
     * Prüft ob das Feld begehbar ist.
     * 
     * @return true wenn das Feld begehbar ist, false sonst
     */
    public boolean isWalkable() {
        return this == PATH || this == START || this == GOAL;
    }
}