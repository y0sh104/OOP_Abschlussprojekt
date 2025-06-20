package model;

/**
 * Each Field is of a specific type. Additionally we define which field types are walkable and which are not.
 */
public enum FieldType {
    /** wall - non walkable */
    WALL,
    /** path - walable */
    PATH,
    /** start location */
    START,
    /** goal location
     */
    GOAL;
    
    /**
     * Check wether field is walkable.
     * 
     * @return true if walkable, false if not
     */
    boolean isWalkable() {
        return this == PATH || this == START || this == GOAL;
    }
}