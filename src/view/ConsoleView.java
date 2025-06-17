package view;

import model.World;

/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {

	@Override
	public void update(World world) {
		// The player's position
		int playerX = world.getPlayerX();
		int playerY = world.getPlayerY();
		

		for (int row = 0; row < world.getHeight(); row++) {
			for (int col = 0; col < world.getWidth(); col++) {
				boolean enemyPos = false;
				for (int enemy = 0; enemy < world.getEnemyList().size(); enemy++) {
					if (row == world.getEnemyList().get(enemy).getEnemyY() && col == world.getEnemyList().get(enemy).getEnemyX()) {
						if (row == playerY && col == playerX) {
							System.out.print("C");
							enemyPos = true;
						} else {
							System.out.print("^");
							enemyPos = true;
						}
					
					}
				}
				// If the player is here, print #, otherwise print .
				if (row == playerY && col == playerX && enemyPos == false) {
					System.out.print("#");
				} else if (enemyPos == false) {
					System.out.print(".");
				}
			}

			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
		for (int enemy = 0; enemy < world.getEnemyList().size(); enemy++) {
			if (playerY == world.getEnemyList().get(enemy).getEnemyY() && playerX == world.getEnemyList().get(enemy).getEnemyX()) {
				System.out.println("GAME OVER!!!");
			}
		}
	}

}
