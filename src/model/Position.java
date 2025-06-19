package model;

/**
 * Das ist eine Position auf dem Spielfeld
 */
public class Position {
    private final int x;
    private final int y;
    
    /**
     * Erstellt eine neue Position.
     * 
     * @param x x-Koordinate
     * @param y y-Koordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return x-Koordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * @return y-Koordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Erstellt eine neue Position basierend auf einer Richtung.
     * 
     * @param direction die Richtung der Bewegung
     * @return neue Position nach der Bewegung
     */
    public Position move(Direction direction) {
        return new Position(x + direction.deltaX, y + direction.deltaY);
    }
    
    /**
     * Prüft ob diese Position gleich einer anderen ist.
     * 
     * @param other die andere Position
     * @return true wenn Positionen gleich sind
     */
    public boolean equals(Position other) {
        return this.x == other.x && this.y == other.y;
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}