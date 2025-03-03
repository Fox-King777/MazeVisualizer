package pathfinding;

import java.util.*;

public class DFS {
	public static int pathfind(int[][] maze, int x, int y, List<int[]> path, List<int[]> searchArea) {
		// Destination Node Found
		if (maze[y][x] == 9) {

			// Add Square to Path
			searchArea.add(new int[] {x, y});

			return 1;
		}

		if (maze[y][x] == 0) {
			maze[y][x] = 3;

			// Add Square to Search Area
			searchArea.add(new int[] {x, y});

			// Check Adjacent Squares
			int[] dx = { 1, 0, -1, 0 };
			int[] dy = { 0, 1, 0, -1 };
			
			for (int i = 0; i < 4; i++) {
				if (pathfind(maze, x + dx[i], y + dy[i], path, searchArea) == 1) {

					// Add Square to Path
					path.add(new int[] {x, y});

					return 1;
				}
			}
		}

		return 0;
	}
}
