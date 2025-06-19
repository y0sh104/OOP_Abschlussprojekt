package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import model.FieldType;
import model.World;

/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {

	/** The view's width. */
	private final int WIDTH;
	/** The view's height. */
	private final int HEIGHT;

	private Dimension fieldDimension;

	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;
		this.bg = new Rectangle(WIDTH, HEIGHT);
	}

	/** The background rectangle. */
	@SuppressWarnings("unused")
	private final Rectangle bg;
	/** The rectangle we're moving. */
	private final Rectangle player = new Rectangle(1, 1);


	@Override
	public void update(World world) {
		// World-Referenz speichern für paintComponent
		this.currentWorld = world;
		
		// Update players size and location
		player.setSize(fieldDimension);
		player.setLocation(
			(int) (world.getPlayerX() * fieldDimension.width),
			(int) (world.getPlayerY() * fieldDimension.height)
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

		// Hintergrund weiß
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// World-Referenz für das Zeichnen benötigt
		// Diese wird temporär gespeichert
		if (currentWorld != null) {
			drawLabyrinth(g, currentWorld);
			drawPlayer(g);
		}
	}
	
	private World currentWorld;
	
	private void drawLabyrinth(Graphics g, World world) {
		for (int y = 0; y < world.getHeight(); y++) {
			for (int x = 0; x < world.getWidth(); x++) {
				FieldType fieldType = world.getFieldType(x, y);
				
				int drawX = x * fieldDimension.width;
				int drawY = y * fieldDimension.height;
				
				// Farbe je nach Feldtyp setzen
				switch (fieldType) {
					case WALL:
						g.setColor(Color.BLACK);
						break;
					case PATH:
						g.setColor(Color.LIGHT_GRAY);
						break;
					case START:
						g.setColor(Color.GREEN);
						break;
					case GOAL:
						g.setColor(Color.ORANGE);
						break;
					default:
						g.setColor(Color.WHITE);
				}
				
				g.fillRect(drawX, drawY, fieldDimension.width, fieldDimension.height);
				
				// Schwarzer Rand um jedes Feld
				g.setColor(Color.DARK_GRAY);
				g.drawRect(drawX, drawY, fieldDimension.width, fieldDimension.height);
			}
		}
	}
	
	private void drawPlayer(Graphics g) {
		// Spieler als blauer Kreis
		g.setColor(Color.BLUE);
		int margin = 3;
		g.fillOval(
			player.x + margin, 
			player.y + margin, 
			player.width - 2*margin, 
			player.height - 2*margin
		);
	}

}
