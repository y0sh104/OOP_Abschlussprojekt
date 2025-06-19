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

	private final int WIDTH;
	private final int HEIGHT;

	private final Dimension fieldSize;
	@SuppressWarnings("unused")
	private final Rectangle bg;
	private final Rectangle player = new Rectangle(1, 1);

	private BufferedImage playerImage;
	private World currentWorld;

	public GraphicView(int width, int height, Dimension fieldSize) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldSize = fieldSize;
		this.bg = new Rectangle(WIDTH, HEIGHT);

		try {
			InputStream stream = getClass().getResourceAsStream("mouse.png");
			playerImage = ImageIO.read(stream);
		} catch (IOException | NullPointerException e) {
			System.err.println("Error loading player image: " + e.getMessage());
		}
	}

	@Override
	public void update(World world) {
		this.currentWorld = world;
		player.setSize(fieldSize);
		player.setLocation(
				world.getPlayerX() * fieldSize.width,
				world.getPlayerY() * fieldSize.height
		);
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
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
	}


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
		