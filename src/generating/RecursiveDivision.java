package generating;

public class RecursiveDivision {
	public static void generate(int[][] maze, int startX, int startY, int endX, int endY) {
		// Calculate x and y length's of section
		int xLength = endX - startX;
		int yLength = endY - startY;
		
		// Base Case
		if (xLength < 2 || yLength < 2) {
			return;
		}
		
		// Horizontal Wall
		if (yLength > xLength) {
			// X Coordinate of Hole in Wall
			int xPassage = (int) (Math.random() * (xLength + 1) + startX);

			// Y Coordinate of Wall
			int yWall = (int) (Math.random() * (yLength - 1)) + (startY + 1);

			// Create Wall
			for (int i = startX; i <= endX; i++) {
				if (i != xPassage) {
					maze[yWall][i] = 1;
				}
			}
			
			// Create Passage if Cross Section
			if (maze[yWall][startX - 1] == 0) {
				maze[yWall][startX] = 0;

			} else if (maze[yWall][endX + 1] == 0) {
				maze[yWall][endX] = 0;
			}

			// Above Wall
			generate(maze, startX, startY, endX, yWall - 1);

			// Below Wall
			generate(maze, startX, yWall + 1, endX, endY);
		
		// Vertical Wall
		} else {
			// Y Coordinate of Hole in Wall
			int yPassage = (int) (Math.random() * (yLength + 1) + startY);

			// X Coordinate of Wall
			int xWall = (int) (Math.random() * (xLength - 1) + (startX + 1));

			// Create Wall
			for (int i = startY; i <= endY; i++) {
				if (i != yPassage) {
					maze[i][xWall] = 1;
				}
			}
			
			// Create Passage if Cross Section
			if (maze[startY - 1][xWall] == 0) {
				maze[startY][xWall] = 0;

			} else if (maze[endY + 1][xWall] == 0) {
				maze[endY][xWall] = 0;
			}

			// Left of Wall
			generate(maze, startX, startY, xWall - 1, endY);

			// Right of Wall
			generate(maze, xWall + 1, startY, endX, endY);
		}
	}
}
