package pathfinding;

import java.util.*;

import visualization.Node;

public class BFS {
	private static Queue<Node> queue = new LinkedList<Node>();

	// Check if Coordinate is Valid in Maze
	private static boolean ValidSquare(int[][] maze, int x, int y) {
		//Checks if the Coordinate is in bounds and isn't a wall or visited before
		if (y >= 1 && y < maze.length - 1 && x >= 1 && x < maze[y].length - 1 && (maze[y][x] == 0 || maze[y][x] == 9)) {
			return true;
		}

		return false;
	}

	private static Node FindPath(int[][] maze, int x, int y, List<int[]> searchArea) {
		// Add Initial Start Position to Queue
		queue.add(new Node(x, y, null));
		maze[x][y] = 3;
		searchArea.add(new int[] {x, y});

		// Adjacent Square Offsets
		int[] dx = { 1, 0, -1, 0 };
		int[] dy = { 0, 1, 0, -1 };

		while (!queue.isEmpty()) {
			Node node = queue.remove();

			for (int i = 0; i < 4; i++) {
				int neighborX = node.getX() + dx[i];
				int neighborY = node.getY() + dy[i];
				// Check Neighbors
				if (ValidSquare(maze, neighborX, neighborY)) {
					// If Target Found
					if (maze[neighborY][neighborX] == 9) {
						return new Node(neighborX, neighborY, node);
					}
					
					// Position Has Been Checked
					maze[neighborY][neighborX] = 3;
					
					// Add Square to Search Area
					searchArea.add(new int[] {neighborX, neighborY});

					// Add New Point to Search Queue
					queue.add(new Node(neighborX, neighborY, node));
				}
			}
		}

		// No Path
		return null;
	}

	public static void pathfind(int[][] maze, int x, int y, List<int[]> path, List<int[]> searchArea) {
		// Conduct BFS
		Node node = FindPath(maze, x, y, searchArea);

		if (node != null) {
			// Back Track Parent Nodes
			while (node.getParent() != null) {

				// Add Parent to Path
				path.add(new int[] {node.getX(), node.getY()});

				// Next Parent
				node = node.getParent();
			}
		}

		// Clear Queue
		queue.clear();
	}
}
