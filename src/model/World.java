package model;

import java.util.ArrayList;

import view.View;
import controller.Controller;

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

	/** Das Labyrinth als 2D-Array */
	private FieldType[][] labyrinth = new FieldType[28][28];
	/** Start-Position im Labyrinth */
	private final Position startPosition = new Position(1, 1);
	/** Ziel-Position im Labyrinth */
	private final Position goalPosition = new Position(27, 27);
	/** Generator für Labyrinthe */

	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();
	private ArrayList<Enemy> enemyList = new ArrayList<>();
	private ArrayList<Wall> wallList = new ArrayList<>();

	/**
	 * Creates a new world with the given size.
	 */
	public World(int width, int height) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
		
		// Labyrinth generieren
		createWalls();
		sortFieldTypes();
		generateNewLabyrinth();
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
	 * Gets the list of enemies.
	 * @return the enemy list
	 */
	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

			/**
	 * Gets the list of walls.
	 * @return returns the wall list
	 */
	public ArrayList<Wall> getWallList() {
		return wallList;
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
		// Grenzen prüfen
		if (playerX < 0 || playerX >= getWidth()) {
			return;
		}
		
		// Gegen die Wand gelaufen
		if (labyrinth[this.playerY][playerX] != null && 
			!labyrinth[this.playerY][playerX].isWalkable()) {
			return;
		}
		
		this.playerX = playerX;
		updateViews();
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
		// Kenne dein Grenzen...
		if (playerY < 0 || playerY >= getHeight()) {
			return;
		}
		
		// ... und lerne die Wände kennen.
		if (labyrinth[playerY][this.playerX] != null && 
			!labyrinth[playerY][this.playerX].isWalkable()) {
			return;
		}
		
		this.playerY = playerY;
		updateViews();
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
	
	///////////////////////////////////////////////////////////////////////////
	// Enemy Management


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
			if (Math.abs(enemyList.get(enemy).getEnemyX() - getPlayerX()) > Math.abs(enemyList.get(enemy).getEnemyY() - getPlayerY())) {
				if (enemyList.get(enemy).getEnemyX() < getPlayerX()) {
					boolean moveTest = true;
					for (int wall = 0; wall < wallList.size(); wall++) {
						if ((enemyList.get(enemy).getEnemyX() + 1) == wallList.get(wall).getWallX() && (enemyList.get(enemy).getEnemyY()) == wallList.get(wall).getWallY()) {
							moveTest = false;
							}
					}
					if (moveTest) {
						enemyList.get(enemy).setEnemyX(enemyList.get(enemy).getEnemyX() + 1);
					}
				}
				else if (enemyList.get(enemy).getEnemyX() > getPlayerX()) {
					boolean moveTest = true;
					for (int wall = 0; wall < wallList.size(); wall++) {
						if ((enemyList.get(enemy).getEnemyX() - 1) == wallList.get(wall).getWallX() && (enemyList.get(enemy).getEnemyY()) == wallList.get(wall).getWallY()) {
							moveTest = false;
							}
					}
					if (moveTest) {
						enemyList.get(enemy).setEnemyX(enemyList.get(enemy).getEnemyX() - 1);
					}
				}
			}
			else {
				if (enemyList.get(enemy).getEnemyY() < getPlayerY()) {
					boolean moveTest = true;
					for (int wall = 0; wall < wallList.size(); wall++) {
						if ((enemyList.get(enemy).getEnemyX()) == wallList.get(wall).getWallX() && (enemyList.get(enemy).getEnemyY() + 1) == wallList.get(wall).getWallY()) {
							moveTest = false;
							}
					}
					if (moveTest) {
						enemyList.get(enemy).setEnemyY(enemyList.get(enemy).getEnemyY() + 1);
					}
				}
				else if (enemyList.get(enemy).getEnemyY() > getPlayerY()) {
					boolean moveTest = true;
					for (int wall = 0; wall < wallList.size(); wall++) {
						if ((enemyList.get(enemy).getEnemyX()) == wallList.get(wall).getWallX() && (enemyList.get(enemy).getEnemyY() - 1) == wallList.get(wall).getWallY()) {
							moveTest = false;
							}
					}
					if (moveTest) {
						enemyList.get(enemy).setEnemyY(enemyList.get(enemy).getEnemyY() - 1);
					}
				}
			} 
		}

		updateViews();
	}

	/**
	 * Creates the enemies. Dependend on the difficulty seleccted in the GUI.
	 * @param difficulty determines how many enemies are created
	 */
	public void createEnemies(int difficulty) {
		if (difficulty == 1) {
			enemyList.add(new Enemy(3, 5, this));
			enemyList.add(new Enemy(7, 9, this));
			enemyList.add(new Enemy(5, 2, this));
		}
		updateViews();
	}


	///////////////////////////////////////////////////////////////////////////
	// Labyrinth Management
	
	/**
	 * Rufe das wirklich dumme Labyrinth auf.
	 */
	public void generateNewLabyrinth() {
		// Start- und Zielpositionen finden
		getStartPosition();
		getGoalPosition();

		getWallList(); // TODO
		
		// Spieler auf Startposition setzen
		this.playerX = startPosition.getX();
		this.playerY = startPosition.getY();

		enemyList.clear();
		createEnemies(Controller.getDifficulty());
		
		updateViews();
	}
	
	
	/**
	 * Gibt den Feldtyp an der angegebenen Position zurück.
	 * 
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 * @return Feldtyp oder null wenn außerhalb der Grenzen
	 */
	public FieldType getFieldType(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return null;
		}
		return labyrinth[x][y];
	}
	
	/**
	 * Prüft ob der Spieler das Ziel erreicht hat.
	 * 
	 * @return true wenn Spieler am Ziel ist
	 */
	public boolean isPlayerAtGoal() {
		return goalPosition != null && 
			   playerX == goalPosition.getX() && 
			   playerY == goalPosition.getY();
	}
	
	/**
	 * @return Start-Position
	 */
	public Position getStartPosition() {
		return startPosition;
	}
	
	/**
	 * @return Ziel-Position
	 */
	public Position getGoalPosition() {
		return goalPosition;
	}

	///////////////////////////////////////////////////////////////////////////
	// Wall Management

	public void createWalls() {
		for (int x = 0; x < 29; x++) {
			wallList.add(new Wall(x, 0));
		}
		wallList.add(new Wall(0, 1));
		wallList.add(new Wall(27, 1));
		wallList.add(new Wall(0, 2));
		wallList.add(new Wall(2, 2));
		wallList.add(new Wall(3, 2));
		wallList.add(new Wall(4, 2));
		wallList.add(new Wall(5, 2));
		wallList.add(new Wall(7, 2));
		wallList.add(new Wall(8, 2));
		wallList.add(new Wall(10, 2));
		wallList.add(new Wall(11, 2));
		wallList.add(new Wall(12, 2));
		wallList.add(new Wall(13, 2));
		wallList.add(new Wall(14, 2));
		wallList.add(new Wall(15, 2));
		wallList.add(new Wall(16, 2));
		wallList.add(new Wall(17, 2));
		wallList.add(new Wall(19, 2));
		wallList.add(new Wall(20, 2));
		wallList.add(new Wall(22, 2));
		wallList.add(new Wall(23, 2));
		wallList.add(new Wall(24, 2));
		wallList.add(new Wall(25, 2));
		wallList.add(new Wall(27, 2));
		wallList.add(new Wall(2, 3));
		wallList.add(new Wall(3, 3));
		wallList.add(new Wall(4, 3));
		wallList.add(new Wall(5, 3));
		wallList.add(new Wall(7, 3));
		wallList.add(new Wall(8, 3));
		wallList.add(new Wall(10, 3));
		wallList.add(new Wall(12, 3));
		wallList.add(new Wall(13, 3));
		wallList.add(new Wall(14, 3));
		wallList.add(new Wall(15, 3));
		wallList.add(new Wall(11, 3));
		wallList.add(new Wall(16, 3));
		wallList.add(new Wall(17, 3));
		wallList.add(new Wall(19, 3));
		wallList.add(new Wall(20, 3));
		wallList.add(new Wall(22, 3));
		wallList.add(new Wall(23, 3));
		wallList.add(new Wall(24, 3));
		wallList.add(new Wall(25, 3));
		wallList.add(new Wall(2, 4));
		wallList.add(new Wall(3, 4));
		wallList.add(new Wall(4, 4));
		wallList.add(new Wall(5, 4));
		wallList.add(new Wall(7, 4));
		wallList.add(new Wall(8, 4));
		wallList.add(new Wall(10, 4));
		wallList.add(new Wall(11, 4));
		wallList.add(new Wall(16, 4));
		wallList.add(new Wall(17, 4));
		wallList.add(new Wall(19, 4));
		wallList.add(new Wall(20, 4));
		wallList.add(new Wall(22, 4));
		wallList.add(new Wall(23, 4));
		wallList.add(new Wall(24, 4));
		wallList.add(new Wall(25, 4));
		

	}

	public void sortFieldTypes() {
		for (int x = 0; x < 28; x++) {
			for (int y = 0; y < 28; y++) {
				for (int wall = 0; wall < wallList.size(); wall++) {
					if (wallList.get(wall).getWallX() == x && wallList.get(wall).getWallY() == y) {
						labyrinth[x][y] = FieldType.WALL;
					}
					else if (x == 1 && y == 1) {
						labyrinth[1][1] = FieldType.START;
					}
					else if (x == 27 && y == 27) {
						labyrinth[27][27] = FieldType.GOAL;
					}
					else {
						labyrinth[x][y] = FieldType.PATH;
					}
					}
				}
			}
		}

}
