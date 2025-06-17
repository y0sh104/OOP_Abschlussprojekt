package model;

import java.util.ArrayList;

import view.View;

/**
 * The world is our model. It saves the bare minimum of information required to
 * accurately reflect the state of the game. Note how this does not know
 * anything about graphics.
 */
public class World {

	/** The world's width. */
	private final int width;
	/** The world's height. */
	private final int height;
	/** The player's x position in the world. */
	private int playerX = 0;
	/** The player's y position in the world. */
	private int playerY = 0;

	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();
	private ArrayList<Enemy> enemyList = new ArrayList<>();


	/**
	 * Creates a new world with the given size.t
	 */
	public World(int width, int height) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
	}

	///////////////////////////////////////////////////////////////////////////
	// Getters and Setters

	/**
	 * Returns the width of the world.
	 * 
	 * @return the width of the world.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the world.
	 * 
	 * @return the height of the world.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the player's x position.
	 * 
	 * @return the player's x position.
	 */
	public int getPlayerX() {
		return playerX;
	}

	/**
	 * Sets the player's x position.
	 * 
	 * @param playerX the player's x position.
	 */
	public void setPlayerX(int playerX) {
		playerX = Math.max(0, playerX);
		playerX = Math.min(getWidth() - 1, playerX);
		this.playerX = playerX;
	}

	/**
	 * Returns the player's y position.
	 * 
	 * @return the player's y position.
	 */
	public int getPlayerY() {
		return playerY;
	}

	/**
	 * Sets the player's y position.
	 * 
	 * @param playerY the player's y position.
	 */
	public void setPlayerY(int playerY) {
		playerY = Math.max(0, playerY);
		playerY = Math.min(getHeight() - 1, playerY);
		this.playerY = playerY;
	}

	///////////////////////////////////////////////////////////////////////////
	// Player Management
	
	/**
	 * Moves the player along the given direction.
	 * 
	 * @param direction where to move.
	 */
	public void movePlayer(Direction direction) {	
		// The direction tells us exactly how much we need to move along
		// every direction
		setPlayerX(getPlayerX() + direction.deltaX);
		setPlayerY(getPlayerY() + direction.deltaY);
	}

	///////////////////////////////////////////////////////////////////////////
	// Enemy Management

	public void registerEnemy(Enemy enemy) {
		enemyList.add(enemy);
	}

	/**
	 * Moves the enemies in relation to the players position.
	 *
	 */
	public void moveEnemies() {

		for (int enemy = 0; enemy < enemyList.size(); enemy++) {
			if (enemyList.get(enemy).getEnemyX() == getPlayerX() && enemyList.get(enemy).getEnemyY() == getPlayerY()) {
				updateViews();
				return;
			}
		}

		for (int enemy = 0; enemy < enemyList.size(); enemy++) {
			if (enemyList.get(enemy).getEnemyX() < getPlayerX()) {
				enemyList.get(enemy).setEnemyX(enemyList.get(enemy).getEnemyX() + 1);
			}
			else if (enemyList.get(enemy).getEnemyX() > getPlayerX()) {
				enemyList.get(enemy).setEnemyX(enemyList.get(enemy).getEnemyX() -1);
			}
			if (enemyList.get(enemy).getEnemyY() < getPlayerY()) {
				enemyList.get(enemy).setEnemyY(enemyList.get(enemy).getEnemyY() + 1);
			}
			else if (enemyList.get(enemy).getEnemyY() > getPlayerY()) {
				enemyList.get(enemy).setEnemyY(enemyList.get(enemy).getEnemyY() -1);
			}
		}

		updateViews();
	}

	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public void createEnemies(int difficulty) {
		if (difficulty == 1) {
			registerEnemy(new Enemy(3, 5, this));
			registerEnemy(new Enemy(7, 12, this));
			registerEnemy(new Enemy(5, 2, this));
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// View Management

	/**
	 * Adds the given view of the world and updates it once. Once registered through
	 * this method, the view will receive updates whenever the world changes.
	 * 
	 * @param view the view to be registered.
	 */
	public void registerView(View view) {
		views.add(view);
		view.update(this);
	}

	/**
	 * Updates all views by calling their {@link View#update(World)} methods.
	 */
	private void updateViews() {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).update(this);
		}
	}

}
