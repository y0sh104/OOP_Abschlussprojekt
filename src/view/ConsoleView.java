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
				int toPrint = 0;
				boolean enemyPos = false;
				for (int wall = 0; wall < world.getHorizontalWallList().size(); wall++) {
					if (row == world.getHorizontalWallList().get(wall).getWallY() && col == world.getHorizontalWallList().get(wall).getWallX()) {
						toPrint = 1;
					}
				}
				for (int wall = 0; wall < world.getVerticalWallList().size(); wall++) {
					if (row == world.getVerticalWallList().get(wall).getWallY() && col == world.getVerticalWallList().get(wall).getWallX()) {
						toPrint = 2;
					}
				}
				for (int enemy = 0; enemy < world.getEnemyList().size(); enemy++) {
					if (row == world.getEnemyList().get(enemy).getEnemyY() && col == world.getEnemyList().get(enemy).getEnemyX()) {
						if (row == playerY && col == playerX) {
							toPrint = 99;
							enemyPos = true;
						} else {
							toPrint = 3;
							enemyPos = true;
						}
					
					}
				}
				if (row == playerY && col == playerX && enemyPos == false) {
					toPrint = 4;
				}
				if (row == 9 && col == 9) {
					toPrint = 98;
				}

				switch (toPrint) {
					case 0:
						System.out.print(".");
						break;
					case 1:
						System.out.print("_");
						break;
					case 2:
						System.out.print("|");
						break;
					case 3:
						System.out.print("^");
						break;
					case 4:
						System.out.print("#");
						break;
					case 98:
						System.out.println("G");
						break;
					case 99:
						System.out.print("C");
						break;
				}
			}

			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
		for (int enemy = 0; enemy < world.getEnemyList().size(); enemy++) {
			if (playerY == world.getEnemyList().get(enemy).getEnemyY() && playerX == world.getEnemyList().get(enemy).getEnemyX()) {
				System.out.println("GAME OVER!\n");
			}
		}
	}

}
