package visualization;

public class Maze {
	// Maze Dimensions
	private static int HEIGHT = 39;
	private static int WIDTH = 55;

	// Array to Store Maze
	public static int[][] maze = new int[Maze.HEIGHT][Maze.WIDTH];

	// 0 = empty
	// 1 = wall
	// 2 = start
	// 3 = searched
	// 9 = destination

	// Empty Maze
	public static void clearMaze() {
		for (int i = 0; i < Maze.WIDTH; i++) {
			for (int j = 0; j < Maze.HEIGHT; j++) {

				if (i == 0 || i == Maze.WIDTH - 1 || j == 0 || j == Maze.HEIGHT - 1) {
					Maze.maze[j][i] = 1;
				} else {
					Maze.maze[j][i] = 0;
				}
			}
		}
	}

	// Clear Path Finding Squares
	public static void clearPath() {
		for (int i = 1; i < Maze.WIDTH - 1; i++) {
			for (int j = 1; j < Maze.HEIGHT - 1; j++) {

				if (Maze.maze[j][i] != 0 && Maze.maze[j][i] != 1 && Maze.maze[j][i] != 2 && Maze.maze[j][i] != 9) {
					Maze.maze[j][i] = 0;
				}
			}
		}
	}

	// Set Entire Maze to Walls
	public static void WallMaze() {
		for (int i = 0; i < Maze.WIDTH; i++) {
			for (int j = 0; j < Maze.HEIGHT; j++) {
				Maze.maze[j][i] = 1;
			}
		}
	}

	// Height Getter
	public static int getHeight() {
		return Maze.HEIGHT;
	}

	// Width Getter
	public static int getWidth() {
		return Maze.WIDTH;
	}
}
