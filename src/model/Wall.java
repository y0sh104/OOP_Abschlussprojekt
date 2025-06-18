package model;

public class Wall {
    /**The enemies' x position in the world */
    private int wallX;
    /**The enemies' y position in the world */
    private int wallY;

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
	 * Returns the wall's x position.
	 * 
	 * @return the wall's x position.
	 */
	public int getWallY() {
		return wallY;
	}
}