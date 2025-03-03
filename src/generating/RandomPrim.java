package generating;

import java.util.*;

public class RandomPrim {
	// Set Entire Maze to Walls
		private static void WallMaze(int[][] maze, int x, int y) {
			for (int i = 0; i < y; i++) {
				for (int j = 0; j < x; j++) {
					maze[i][j] = 1;
				}
			}
		}

		// Add Border of Walls Around Maze
		private static void EdgeWall(int[][] maze, int x, int y) {
			for (int i = 0; i < x; i++) {
				maze[0][i] = 1;
				maze[y - 1][i] = 1;
			}

			for (int i = 0; i < y; i++) {
				maze[i][0] = 1;
				maze[i][x - 1] = 1;
			}
		}

		public static void generate(int[][] maze, int x, int y) {
			// Block Entire Maze
			WallMaze(maze, x, y);

			// Linked List to Store Passages
			LinkedList<int[]> passages = new LinkedList<>();

			Random random = new Random();

			// Random Start Position
			int xr = (int) (Math.random() * (x - 1) + 1);
			int yr = (int) (Math.random() * (y - 1) + 1);

			// Add Initial Passage to List
			int[] initialPassage = { yr, xr, yr, xr };
			passages.add(initialPassage);

			while (!passages.isEmpty()) {
				// Randomly Select a New Passage
				int[] p = passages.remove(random.nextInt(passages.size()));
				yr = p[2];
				xr = p[3];

				if (maze[yr][xr] == 1) {

					// Clear Passage
					maze[p[0]][p[1]] = 0;
					maze[yr][xr] = 0;

					// Add New Passages to List
					if (yr >= 2 && maze[yr - 2][xr] == 1) {
						int[] newPassage = { yr - 1, xr, yr - 2, xr };
						passages.add(newPassage);
					}

					if (xr >= 2 && maze[yr][xr - 2] == 1) {
						int[] newPassage = { yr, xr - 1, yr, xr - 2 };
						passages.add(newPassage);
					}

					if (yr < y - 2 && maze[yr + 2][xr] == 1) {
						int[] newPassage = { yr + 1, xr, yr + 2, xr };
						passages.add(newPassage);
					}

					if (xr < x - 2 && maze[yr][xr + 2] == 1) {
						int[] newPassage = { yr, xr + 1, yr, xr + 2 };
						passages.add(newPassage);
					}
				}
			}

			// Add Border of Walls
			EdgeWall(maze, x, y);

			// Clear Frontiers List
			passages.clear();
		}
}
