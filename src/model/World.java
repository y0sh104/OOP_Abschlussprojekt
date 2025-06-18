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

	@SuppressWarnings("unused")
	private boolean blockArrowInput;

	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();
	private ArrayList<Enemy> enemyList = new ArrayList<>();
	private ArrayList<Wall> horizontalWallList = new ArrayList<>();
	private ArrayList<Wall> verticalWallList = new ArrayList<>();


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

	public boolean getBlock() {
		for (int enemy = 0; enemy < enemyList.size(); enemy++) {
			if (enemyList.get(enemy).getEnemyX() == getPlayerX() && enemyList.get(enemy).getEnemyY() == getPlayerY()) {
				return blockArrowInput = true;
			}
		}
		return blockArrowInput = false;
	}

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

	/**
	 * Adds an enemy to the enemy list.
	 * @param enemy the enemy to add to the list
	 */
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
			if (Math.abs(enemyList.get(enemy).getEnemyX() - getPlayerX()) >= Math.abs(enemyList.get(enemy).getEnemyY() - getPlayerY())) {
				if (enemyList.get(enemy).getEnemyX() < getPlayerX()) {
				enemyList.get(enemy).setEnemyX(enemyList.get(enemy).getEnemyX() + 1);
				}
				else if (enemyList.get(enemy).getEnemyX() > getPlayerX()) {
				enemyList.get(enemy).setEnemyX(enemyList.get(enemy).getEnemyX() -1);
				}
			}
			else {
				if (enemyList.get(enemy).getEnemyY() < getPlayerY()) {
				enemyList.get(enemy).setEnemyY(enemyList.get(enemy).getEnemyY() + 1);
				}
				else if (enemyList.get(enemy).getEnemyY() > getPlayerY()) {
				enemyList.get(enemy).setEnemyY(enemyList.get(enemy).getEnemyY() -1);
				}
			} 
		}

		updateViews();
	}

	/**
	 * Gets the list of enemies.
	 * @return the enemy list
	 */
	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	/**
	 * Creates the enemies. Dependend on the difficulty seleccted in the GUI.
	 * @param difficulty determines how many enemies are created
	 */
	public void createEnemies(int difficulty) {
		if (difficulty == 1) {
			registerEnemy(new Enemy(3, 5, this));
			registerEnemy(new Enemy(7, 9, this));
			registerEnemy(new Enemy(5, 2, this));
		}
		updateViews();
	}

	///////////////////////////////////////////////////////////////////////////
	// Wall Management

	/**
	 * Adds an wall to the enemy list.
	 * @param wall the wall to add to the list
	 */
	 public void registerHorizontalWall(Wall wall) {
		horizontalWallList.add(wall);
	}

	/**
	 * Adds an wall to the enemy list.
	 * @param wall the wall to add to the list
	 */
	 public void registerVerticalWall(Wall wall) {
		verticalWallList.add(wall);
	}

	/**
	 * Gets the list of walls.
	 * @return returns the wall list
	 */
	public ArrayList<Wall> getHorizontalWallList() {
		return horizontalWallList;
	}

		/**
	 * Gets the list of walls.
	 * @return returns the wall list
	 */
	public ArrayList<Wall> getVerticalWallList() {
		return verticalWallList;
	}

	public void createHorizontalWalls() {
		registerHorizontalWall(new Wall(2, 3));
		registerHorizontalWall(new Wall(5, 4));
		registerHorizontalWall(new Wall(6, 4));
		registerHorizontalWall(new Wall(2, 3));
		registerHorizontalWall(new Wall(8, 9));
		registerHorizontalWall(new Wall(4, 7));
	}

	public void createVerticalWalls() {
		registerVerticalWall(new Wall(7, 8));
		registerVerticalWall(new Wall(6, 6));
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
