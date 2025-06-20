package model;

/**
 * Class for the walls on the game board.
 */
public class Wall {
    /**The wall's x position in the world */
    private int wallX;
    /**The wall's y position in the world */
    private int wallY;

	/**
	 * Creates a new wall based on a wallX and wallY parameter.
	 * @param wallX walls x coordinate
	 * @param wallY walls y coordinate
	 */
    public Wall(int wallX, int wallY) {
        this.wallX = wallX;
        this.wallY = wallY;
    }

    /**
	 * Returns the wall's x position.
	 * 
	 * @return the wall's x position.
	 */
	public int getWallX() {
		return wallX;
	}

    /**
	 * Returns the wall's y position.
	 * 
	 * @return the wall's y position.
	 */
	public int getWallY() {
		return wallY;
	}
}