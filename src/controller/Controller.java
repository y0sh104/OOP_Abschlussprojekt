package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Direction;
import model.World;
import view.View;

/**
 * Our controller listens for key events on the main window and can reset the game.
 */
public class Controller extends JFrame implements KeyListener, ActionListener, MouseListener {

	/** The world that is updated upon every key press. */
	private World world;
	/** The game's difficulty that controls the enemy count */
	private static int difficulty = 1;

	/** Holds information about wether player reached goal or not */
	private boolean goalReached = false;

	@SuppressWarnings("unused")
	private List<View> views;

	/**
	 * Creates a new instance.
	 * 
	 * @param world the world to be updated whenever the player should move.
	 * @param caged the {@link GraphicsProgram} we want to listen for key presses
	 *              on.
	 */
	public Controller(World world) {
		// Remember the world
		this.world = world;

		// Listen for key events
		addKeyListener(this);
		// Listen for mouse events.
		// Not used in the current implementation.
		addMouseListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	//////// Difficulty //////

	/**
	 * Gets the games difficulty setting.
	 * @return returns the difficulty
	 */
	public static int getDifficulty() {
		return difficulty;
	}

	/**
	 * Sets the games difficulty to the given paramteter and restarts the game.
	 * @param diff the difficulty to set
	 */
	public void setDifficulty(int diff) {
		difficulty = diff;
		resetGame();
	}

	///////// Restart ////////

	/**
	 * Resets the game into its starting state, according to the game's difficulty And controls messages on new game.
	 */
	public void resetGame() {
		goalReached = false;
		this.requestFocusInWindow();
		System.out.println("NEW GAME! Difficulty: " + difficulty + "\n");
		JOptionPane.showMessageDialog(this, "NEW GAME! Difficulty: " + difficulty);
		world.setPlayerX(1);
		world.setPlayerY(1);
		world.getEnemyList().clear();
		world.createEnemies(difficulty);
	}

	/////////////////// Key Events ////////////////////////////////

	@Override
	public void keyPressed(KeyEvent e) {
		// Check if we need to do something. Tells the world to move the player.
		if (goalReached) //if goal is reached inputs are ignored
			return;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (!world.getBlock()) {
					world.movePlayer(Direction.UP);
					world.moveEnemies();
				}
				break;

			case KeyEvent.VK_DOWN:
				if (!world.getBlock()) {
					world.movePlayer(Direction.DOWN);
					world.moveEnemies();
				}
				break;

			case KeyEvent.VK_LEFT:
				if (!world.getBlock()) {
					world.movePlayer(Direction.LEFT);
					world.moveEnemies();
				}
				break;

			case KeyEvent.VK_RIGHT:
				if (!world.getBlock()) {
					world.movePlayer(Direction.RIGHT);
					world.moveEnemies();
				}
				break;
		}
		if (world.isPlayerAtGoal()) {//if player is at goal, output message and restart game
			javax.swing.JOptionPane.showMessageDialog(this, "Congratulations! You reached the goal!");
			goalReached = true;
			resetGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/////////////////// Action Events ////////////////////////////////

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/////////////////// Mouse Events ////////////////////////////////

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
