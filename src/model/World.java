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

	/** Das Labyrinth als 2D-Array */
	private FieldType[][] labyrinth;
	/** Start-Position im Labyrinth */
	private Position startPosition;
	/** Ziel-Position im Labyrinth */
	private Position goalPosition;
	/** Generator für Labyrinthe */
	private final LabyrinthGenerator generator;

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
		this.generator = new LabyrinthGenerator();
		
		// Labyrinth generieren
		generateNewLabyrinth();
	}
	public void regenerate() {
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
			registerEnemy(new Enemy(3, 5, this));
			registerEnemy(new Enemy(7, 9, this));
			registerEnemy(new Enemy(5, 2, this));
		}
		updateViews();
	}


	///////////////////////////////////////////////////////////////////////////
	// Labyrinth Management
	
	/**
	 * Rufe das wirklich dumme Labyrinth auf.
	 */
	public void generateNewLabyrinth() {
		// Einfaches Labyrinth für den Anfang
		this.labyrinth = generator.generateSimpleLabyrinth(width, height);
		
		// Start- und Zielpositionen finden
		findStartAndGoalPositions();
		
		// Spieler auf Startposition setzen
		if (startPosition != null) {
			this.playerX = startPosition.getX();
			this.playerY = startPosition.getY();
		}
		
		updateViews();
	}
	
	/**
	 * Findet Start- und Zielpositionen im Labyrinth.
	 */
	private void findStartAndGoalPositions() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (labyrinth[y][x] == FieldType.START) {
					startPosition = new Position(x, y);
				} else if (labyrinth[y][x] == FieldType.GOAL) {
					goalPosition = new Position(x, y);
				}
			}
		}
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
		return labyrinth[y][x];
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

	/**
	 * Adds an wall to the enemy list.
	 * @param wall the wall to add to the list
	 */
	 public void registerWall(Wall wall) {
		wallList.add(wall);
	}

	public void createWalls() {
		registerWall(new Wall(1, 1));
		registerWall(new Wall(2, 3));
		registerWall(new Wall(5, 4));
		registerWall(new Wall(6, 4));
		registerWall(new Wall(2, 3));
		registerWall(new Wall(8, 9));
		registerWall(new Wall(4, 7));
		registerWall(new Wall(7, 8));
		registerWall(new Wall(6, 6));
		registerWall(new Wall(3, 6));
		registerWall(new Wall(7, 2));
	}



}
