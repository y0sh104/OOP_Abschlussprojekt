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
 * Our controller listens for key events on the main window.
 */
public class Controller extends JFrame implements KeyListener, ActionListener, MouseListener {

	/** The world that is updated upon every key press. */
	private World world;
	private static int difficulty = 1;

	@SuppressWarnings("unused")
	private List<View> views;
	private boolean goalReached = false;
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
	
	public static int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int diff) {
		difficulty = diff;
	}


	///////// Restart ////////

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
		if (goalReached) return;
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

		case KeyEvent.VK_N:
			resetGame();
			break;

		case KeyEvent.VK_1:
			setDifficulty(1);
			resetGame();
			break;

		case KeyEvent.VK_2:
			setDifficulty(2);
			resetGame();
			break;
		
		case KeyEvent.VK_3:
			setDifficulty(3);
			resetGame();
			break;
		
		}
		if (world.isPlayerAtGoal()) {
			javax.swing.JOptionPane.showMessageDialog(this, "Congratulations! You reached the goal!");
			goalReached = true;
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
