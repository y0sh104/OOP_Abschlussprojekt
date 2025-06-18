package model;

public class Enemy {
    /**The enemies' x position in the world */
    private int enemyX;
    /**The enemies' y position in the world */
    private int enemyY;

    private World world;

    public Enemy(int enemyX, int enemyY, World world) {
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.world = world;
    }

    /**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */
	public int getEnemyX() {
		return enemyX;
	}

    /**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */
	public int getEnemyY() {
		return enemyY;
	}
    
    /**
	 * Sets the player's x position.
	 * 
	 * @param enemyX the player's x position.
	 */
	public void setEnemyX(int enemyX) {
		enemyX = Math.max(0, enemyX);
		enemyX = Math.min(world.getWidth() - 1, enemyX);
		this.enemyX = enemyX;
	}

    /**
	 * Sets the player's x position.
	 * 
	 * @param enemyX the player's x position.
	 */
	public void setEnemyY(int enemyY) {
		enemyY = Math.max(0, enemyY);
		enemyY = Math.min(world.getHeight() - 1, enemyY);
		this.enemyY = enemyY;
	}
}