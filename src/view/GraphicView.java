package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.FieldType;
import model.World;

/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {
	/**the witdh of the view */
	private final int WIDTH;
	/**the heigth of the view */
	private final int HEIGHT;

	/**the dimensions of the window */
	private final Dimension fieldSize;
	@SuppressWarnings("unused")
	/**rectangle background */
	private final Rectangle bg;
	/**player rectangle that is used to track movements */
	private final Rectangle player = new Rectangle(1, 1);

	/**image that is portrayed onto the player rectangle */
	private BufferedImage playerImage;
	/**imgae portryed onto enemies */
	private BufferedImage enemyImage;

	/**variable to ave current world state */
	private World currentWorld;

	/**
	 * Creates new graphical view wiht given paramters of the dimensions.
	 * @param width width of the view
	 * @param height height of the view
	 * @param fieldSize dimension of the window
	*/
	public GraphicView(int width, int height, Dimension fieldSize) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldSize = fieldSize;
		this.bg = new Rectangle(WIDTH, HEIGHT);

		try {
			InputStream stream = getClass().getResourceAsStream("mouse.png");
			playerImage = ImageIO.read(stream);
			InputStream enemyStream = getClass().getResourceAsStream("cat.png");  // Pfad zum Gegnerbild
			enemyImage = ImageIO.read(enemyStream);
		} catch (IOException | NullPointerException e) {
			System.err.println("Error loading player image: " + e.getMessage());
		}
	}

	@Override
	/**
	 * Updates the view
	 * @param world gives the information about the world that need to be updated
	 */
	public void update(World world) {
		this.currentWorld = world;
		player.setSize(fieldSize);
		player.setLocation(
				world.getPlayerX() * fieldSize.width,
				world.getPlayerY() * fieldSize.height
		);
		System.out.println();
		for (int enemy = 0; enemy < world.getEnemyList().size(); enemy++) {
			if (world.getPlayerY() == world.getEnemyList().get(enemy).getEnemyY() && world.getPlayerX() == world.getEnemyList().get(enemy).getEnemyX()) {
				javax.swing.JOptionPane.showMessageDialog(this, "Game over! Youhave been caught!");
			}
		}
		repaint();
	}

	@Override
	/**
	 * Gets the size the dimension should be in
	 * @ return returns a new dimension with preferred height and width
	 */
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	/**
	 * Paints in everything using java.awt.Graphics
	 * @param the base for all the graphic content
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (currentWorld != null) {
			drawLabyrinth(g, currentWorld);

			// Nur Wände zeichnen
			g.setColor(Color.BLACK);
			for (model.Wall wall : currentWorld.getWallList()) {
				int wx = wall.getWallX() * fieldSize.width;
				int wy = wall.getWallY() * fieldSize.height;
				g.fillRect(wx, wy, fieldSize.width, fieldSize.height);
			}

			// Spieler zeichnen
			if (playerImage != null) {
				g.drawImage(playerImage, player.x, player.y, player.width, player.height, null);
			} else {
				drawPlayerFallback(g);
			}
		}
		// Gegner zeichnen
		if (enemyImage != null && currentWorld.getEnemyList() != null) {
			for (model.Enemy enemy : currentWorld.getEnemyList()) {
				int ex = enemy.getEnemyX() * fieldSize.width;
				int ey = enemy.getEnemyY() * fieldSize.height;
				g.drawImage(enemyImage, ex, ey, fieldSize.width, fieldSize.height, null);
			}
		} else {
			// Falls du eine Fallback-Zeichnung möchtest, kannst du sie hier einbauen
		}

	}

	/**
	 * draws everything in the graphics view using the information about world and graphicla elements
	 * @param g information about graphical elements
	 * @param world information about world
	 */
	private void drawLabyrinth(Graphics g, World world) {
		for (int y = 0; y < world.getHeight(); y++) {
			for (int x = 0; x < world.getWidth(); x++) {
				FieldType type = world.getFieldType(x, y);
				int px = x * fieldSize.width;
				int py = y * fieldSize.height;

				switch (type) {
					case WALL:
						g.setColor(Color.BLACK); break;
					case PATH:
						g.setColor(Color.LIGHT_GRAY); break;
					case START:
						g.setColor(Color.GREEN); break;
					case GOAL:
						g.setColor(Color.ORANGE); break;
					default:
						g.setColor(Color.WHITE);
				}

				g.fillRect(px, py, fieldSize.width, fieldSize.height);
				g.setColor(Color.DARK_GRAY);
				g.drawRect(px, py, fieldSize.width, fieldSize.height);
			}
		}
	}

	/**
	 * Draws the fallback version of the player using graphics.
	 * @param g information about graphical elements
	 */
	private void drawPlayerFallback(Graphics g) {
		g.setColor(Color.BLUE);
		int margin = 3;
		g.fillOval(
				player.x + margin,
				player.y + margin,
				player.width - 2 * margin,
				player.height - 2 * margin
		);
	}
}