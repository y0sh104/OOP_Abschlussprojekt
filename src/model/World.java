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

    /**
     * The world's width.
     */
    private final int width;
    /**
     * The world's height.
     */
    private final int height;
    /**
     * The player's x position in the world.
     */
    private int playerX = 0;
    /**
     * The player's y position in the world.
     */
    private int playerY = 0;

    /** the labyrinth as a 2D-array */
    private FieldType[][] labyrinth = new FieldType[28][28];
    /** start position in the labyrinth */
    private final Position startPosition = new Position(1, 1);
    /** goal position in the labyrinth */
    private final Position goalPosition = new Position(26, 26);
    
    @SuppressWarnings("unused")
    /** Holds a value to check wether arrow inputs should be blocked */
    private boolean blockArrowInput;

    /**
     * Set of views registered to be notified of world updates.
     */
    private final ArrayList<View> views = new ArrayList<>();
    /** List of enemies in the game */
    private ArrayList<Enemy> enemyList = new ArrayList<>();
    /** List of walls created on the game board */
    private ArrayList<Wall> wallList = new ArrayList<>();

    /**
     * Creates a new world with the given size.
     */
    public World(int width, int height) {
        // Normally, we would check the arguments for proper values
        this.width = width;
        this.height = height;

        // creates the important elements
        createWalls();
        sortFieldTypes();
        generateNewLabyrinth();
    }

    /// ////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    /**
	 * Gets the block status
	 * @return the wether the arrowinputs are blocked
	 */
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
     *
     * @return the enemy list
     */
    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    /**
     * Gets the list of walls.
     *
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
        int newX = getPlayerX() + direction.deltaX;
        int newY = getPlayerY() + direction.deltaY;

        // Prüfe Grenzen
        if (newX < 0 || newX >= getWidth() || newY < 0 || newY >= getHeight()) {
            return;
        }

        // Prüfe, ob dort eine Wand ist
        for (Wall wall : wallList) {
            if (wall.getWallX() == newX && wall.getWallY() == newY) {
                return; // Bewegung blockiert
            }
        }

        // Alles frei: Spieler bewegen
        playerX = newX;
        playerY = newY;
        updateViews();
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
     * Moves the enemies in relation to the players position. Checks wether walls or other enemies are in the way.
     */
    public void moveEnemies() {
        for (int enemy = 0; enemy < enemyList.size(); enemy++) {
            Enemy current = enemyList.get(enemy);

            // Check for collision with player
            if (current.getEnemyX() == getPlayerX() && current.getEnemyY() == getPlayerY()) {
                updateViews();
                return;
            }
        }

        for (int enemy = 0; enemy < enemyList.size(); enemy++) {
            Enemy current = enemyList.get(enemy);
            int dx = Integer.compare(getPlayerX(), current.getEnemyX()); // -1, 0 or 1
            int dy = Integer.compare(getPlayerY(), current.getEnemyY());

            boolean moved = false;

            // First try moving in the dominant axis
            if (Math.abs(getPlayerX() - current.getEnemyX()) > Math.abs(getPlayerY() - current.getEnemyY())) {
                moved = tryMove(current, dx, 0, enemy);
                if (!moved) moved = tryMove(current, 0, dy, enemy); // fallback
            } else {
                moved = tryMove(current, 0, dy, enemy);
                if (!moved) moved = tryMove(current, dx, 0, enemy); // fallback
            }
        }

        updateViews();
    }

    // Helper method to try moving an enemy in a direction
    private boolean tryMove(Enemy enemy, int dx, int dy, int enemyIndex) {
        int newX = enemy.getEnemyX() + dx;
        int newY = enemy.getEnemyY() + dy;

        // Check wall collision
        for (Wall wall : wallList) {
            if (wall.getWallX() == newX && wall.getWallY() == newY) {
                return false;
            }
        }

        // Check enemy collision (skip self)
        for (int i = 0; i < enemyList.size(); i++) {
            if (i == enemyIndex) continue;
            if (enemyList.get(i).getEnemyX() == newX && enemyList.get(i).getEnemyY() == newY) {
                return false;
            }
        }

        // All clear – move enemy
        enemy.setEnemyX(newX);
        enemy.setEnemyY(newY);
        return true;
    }

    /**
     * Creates the enemies. Dependend on the difficulty seleccted in the GUI.
     *
     * @param difficulty determines how many enemies are created
     */
    public void createEnemies(int difficulty) {
        if (difficulty == 1) {
            enemyList.add(new Enemy(12, 17, this));
        } else if (difficulty == 2) {
            enemyList.add(new Enemy(12, 15, this));
            enemyList.add(new Enemy(17, 18, this));
        }
        updateViews();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Labyrinth Management

    /**
     * Genrates the labyrinth elements
     */
    public void generateNewLabyrinth() {
        // gets start and goal positions
        getStartPosition();
        getGoalPosition();

        // gets the wallList
        getWallList();

        // sets the player to the start position
        this.playerX = startPosition.getX();
        this.playerY = startPosition.getY();

        // clears all enemies on the board and creates new ones according to the difficulty
        enemyList.clear();
        createEnemies(Controller.getDifficulty());

        updateViews();
    }


    /**
	 * Give back field type of a given coordinate pair
	 * 
	 * @param x x position
	 * @param y y position
	 * @return returns the field type of the labyrinth at the given coordinates
	 */
    public FieldType getFieldType(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return labyrinth[x][y];
    }

    /**
	 * Checks wether player reached the goal
	 * 
	 * @return true if player is at the goal
	 */
    public boolean isPlayerAtGoal() {
        return goalPosition != null &&
                playerX == goalPosition.getX() &&
                playerY == goalPosition.getY();
    }

    /**
     * Gets the start position
     * @return Start-Position
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * Gets the goal position
     * @return Ziel-Position
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /// ////////////////////////////////////////////////////////////////////////
    // Wall Management
    /**
	 * Creates the walls with predetermined positions
	 */
    public void createWalls() {// TODO
        for (int x = 0; x < 29; x++) {
            wallList.add(new Wall(x, 0));
        }
        for (int y = 1; y < 20; y++) {
            wallList.add(new Wall(0, y));
        }
        for (int y = 1; y < 20; y++) {
            wallList.add(new Wall(27, y));
        }
        for (int x = 1; x < 27; x++) {
            wallList.add(new Wall(x, 27));
        }
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
        wallList.add(new Wall(7, 5));
        wallList.add(new Wall(8, 5));
        wallList.add(new Wall(10, 5));
        wallList.add(new Wall(11, 5));
        wallList.add(new Wall(13, 5));
        wallList.add(new Wall(14, 5));
        wallList.add(new Wall(16, 5));
        wallList.add(new Wall(17, 5));
        wallList.add(new Wall(19, 5));
        wallList.add(new Wall(20, 5));
        wallList.add(new Wall(2, 6));
        wallList.add(new Wall(3, 6));
        wallList.add(new Wall(5, 6));
        wallList.add(new Wall(6, 6));
        wallList.add(new Wall(7, 6));
        wallList.add(new Wall(8, 6));
        wallList.add(new Wall(10, 6));
        wallList.add(new Wall(11, 6));
        wallList.add(new Wall(13, 6));
        wallList.add(new Wall(14, 6));
        wallList.add(new Wall(16, 6));
        wallList.add(new Wall(17, 6));
        wallList.add(new Wall(19, 6));
        wallList.add(new Wall(20, 6));
        wallList.add(new Wall(21, 6));
        wallList.add(new Wall(22, 6));
        wallList.add(new Wall(24, 6));
        wallList.add(new Wall(25, 6));
        wallList.add(new Wall(2, 7));
        wallList.add(new Wall(3, 7));
        wallList.add(new Wall(5, 7));
        wallList.add(new Wall(6, 7));
        wallList.add(new Wall(7, 7));
        wallList.add(new Wall(8, 7));
        wallList.add(new Wall(10, 7));
        wallList.add(new Wall(11, 7));
        wallList.add(new Wall(13, 7));
        wallList.add(new Wall(14, 7));
        wallList.add(new Wall(16, 7));
        wallList.add(new Wall(17, 7));
        wallList.add(new Wall(19, 7));
        wallList.add(new Wall(20, 7));
        wallList.add(new Wall(21, 7));
        wallList.add(new Wall(22, 7));
        wallList.add(new Wall(24, 7));
        wallList.add(new Wall(25, 7));
        wallList.add(new Wall(2, 8));
        wallList.add(new Wall(3, 8));
        wallList.add(new Wall(7, 8));
        wallList.add(new Wall(8, 8));
        ;
        wallList.add(new Wall(13, 8));
        wallList.add(new Wall(14, 8));
        wallList.add(new Wall(19, 8));
        wallList.add(new Wall(20, 8));
        wallList.add(new Wall(24, 8));
        wallList.add(new Wall(25, 8));
        for (int i = 0; i <= 26; i++) {
            if (i != 1 && i != 6 && i != 9 && i != 18 && i != 21 && i != 26) {
                wallList.add(new Wall(i, 9));
            }
        }
        for (int i = 0; i <= 26; i++) {
            if (i != 1 && i != 6 && i != 9 && i != 18 && i != 21 && i != 26) {
                wallList.add(new Wall(i, 10));
            }
        }
        for (int i = 0; i <= 26; i++) {
            if (i != 3 && i != 9 && i != 13 && i != 14 && i != 18 && i != 24) {
                wallList.add(new Wall(i, 12));
            }
        }
        for (int i = 0; i <= 26; i++) {
            if (i != 3 && i != 9 && i != 11 && i != 12 && i != 13 && i != 14 && i != 15 && i != 16 && i != 18 && i != 24) {
                wallList.add(new Wall(i, 13));
            }
        }
        wallList.add(new Wall(7, 14));
        wallList.add(new Wall(8, 14));
        wallList.add(new Wall(10, 14));
        wallList.add(new Wall(17, 14));
        wallList.add(new Wall(19, 14));
        wallList.add(new Wall(20, 14));
        for (int i = 0; i <= 26; i++) {
            if (i != 3 && i != 6 && i != 9 && i != 11 && i != 12 && i != 13 && i != 14 && i != 15 && i != 16 && i != 18 && i != 21 && i != 24) {
                wallList.add(new Wall(i, 15));
            }
        }
        for (int i = 0; i <= 26; i++) {
            if (i != 3 && i != 6 && i != 9 && i != 18 && i != 21 && i != 24) {
                wallList.add(new Wall(i, 16));
            }
        }
        wallList.add(new Wall(4,17));
        wallList.add(new Wall(5,17));
        wallList.add(new Wall(4,17));
        wallList.add(new Wall(22,17));
        wallList.add(new Wall(23,17));
        for (int i = 0; i <= 26; i++) {
            if (i != 1 && i != 6 && i != 12 && i != 15 && i != 21 && i != 26) {
                wallList.add(new Wall(i, 18));
            }
        }
        for (int i = 0; i <= 26; i++) {
            if (i != 1 && i != 6 && i != 12 && i != 15 && i != 21 && i != 26) {
                wallList.add(new Wall(i, 19));
            }
        }
        wallList.add(new Wall(7,20));
        wallList.add(new Wall(8,20));
        wallList.add(new Wall(13,20));
        wallList.add(new Wall(14,20));
        wallList.add(new Wall(19,20));
        wallList.add(new Wall(20,20));
        for (int i = 0; i <= 27; i++) {
            if (i != 3 && i != 6 && i != 9 && i != 18 && i != 21 && i != 24) {
                wallList.add(new Wall(i, 21));
            }
        }
        for (int i = 0; i <= 27; i++) {
            if (i != 3 && i != 6 && i != 9 && i != 18 && i != 21 && i != 24) {
                wallList.add(new Wall(i, 22));
            }
        }
        wallList.add(new Wall(2,23));
        wallList.add(new Wall(4,23));
        wallList.add(new Wall(5,23));
        wallList.add(new Wall(22,23));
        wallList.add(new Wall(23,23));
        wallList.add(new Wall(25,23));
        wallList.add(new Wall(26,23));
        wallList.add(new Wall(1,23));
        for (int i = 0; i <= 27; i++) {
            if (i != 3 && i != 12 && i != 15 && i != 24) {
                wallList.add(new Wall(i, 24));
            }
        }
        for (int i = 0; i <= 28; i++) {
            wallList.add(new Wall(0, i));
            wallList.add(new Wall(27, i));
        }
        wallList.add(new Wall(4,26));
        wallList.add(new Wall(5,26));
        wallList.add(new Wall(13,26));
        wallList.add(new Wall(14,26));
        wallList.add(new Wall(22,26));
        wallList.add(new Wall(23,26));

    }

    /**
	 * Sorts out the field type for each position in the labyrinth
	 */
    public void sortFieldTypes() {
        for (int x = 0; x < 28; x++) {
            for (int y = 0; y < 28; y++) {
                boolean isWall = false;
                for (int wall = 0; wall < wallList.size(); wall++) {
                    if (wallList.get(wall).getWallX() == x && wallList.get(wall).getWallY() == y) {
                        labyrinth[x][y] = FieldType.WALL;
                        isWall = true;
                        break;
                    }
                }

                if (!isWall) {
                    if (x == startPosition.getX() && y == startPosition.getY()) {
                        labyrinth[x][y] = FieldType.START;
                    } else if (x == goalPosition.getX() && y == goalPosition.getY()) {
                        labyrinth[x][y] = FieldType.GOAL;
                    } else {
                        labyrinth[x][y] = FieldType.PATH;
                    }
                }
            }
        }
    }
}