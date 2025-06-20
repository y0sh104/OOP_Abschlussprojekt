package model;

/**
 * Class for positions on the game board.
 */
public class Position {
    private final int x;
    private final int y;
    
    /**
     * Craetes a new position with the x and y parameters.
     * 
     * @param x x position
     * @param y y position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x position.
     * @return returns the x position
     */
    int getX() {
        return x;
    }
    
    /**
     * Get the y position
     * @return y position
     */
    int getY() {
        return y;
    }
    
    /**
     * Creates a new position based on a direction parameter.
     * 
     * @param direction the direction
     * @return returns new position
     */
    Position move(Direction direction) {
        return new Position(x + direction.deltaX, y + direction.deltaY);
    }
    
    /**
     * Checks wether the current position is the same as an other, given by a paramterter.
     * 
     * @param other position to be checked against
     * @return returns true if positions are the same
     */
    boolean equals(Position other) {
        return this.x == other.x && this.y == other.y;
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}