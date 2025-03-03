package pathfinding;

import java.util.*;

import visualization.Node;

public class AStar {
	// Comparator for F Cost Field
		private static Comparator<Node> AStarSorter = Comparator.comparing(Node::getCost);

		// Store Points With Cost Value Priority
		private static PriorityQueue<Node> priorityQueue = new PriorityQueue<>(AStarSorter);

		// Check if Coordinate is Valid in Maze
		private static boolean ValidSquare(int[][] maze, int x, int y) {
			return (y >= 1 && y < maze.length - 1 && x >= 1 && x < maze[y].length - 1 && (maze[y][x] == 0 || maze[y][x] == 9));
		}

		private static Node AStarPath(int[][] maze, int xStart, int yStart, int xDest, int yDest,
				List<int[]> searchArea) {
			// Add Initial Start Position to Queue
			priorityQueue.add(new Node(xStart, yStart, null, Math.abs(xStart - xDest) + Math.abs(yStart - yDest)));
			maze[yStart][xStart] = 3;
			searchArea.add(new int[] {xStart, yStart});

			// Initialize Costs
			int total_cost = 0;
			int gcost = 0;
			int hcost = 0;

			// Adjacent Square Directions
			int[] dx = { 1, 0, -1, 0 };
			int[] dy = { 0, 1, 0, -1 };

			while (!priorityQueue.isEmpty()) {
				Node node = priorityQueue.poll();

				for (int i = 0; i < 4; i++) {
					int neighborX = node.getX() + dx[i];
					int neighborY = node.getY() + dy[i];
					
					// Check Neighbors
					if (ValidSquare(maze, neighborX, neighborY)) {
						// Create New Point for Adjacent Square
						Node neighbor = new Node(neighborX, neighborY, node);
						
						// Destination Node Found
						if (maze[neighborY][neighborX] == 9) {
							return neighbor;
						}

						// Position Has Been Checked
						maze[neighborY][neighborX] = 3;

						// Add Square to Search Area
						searchArea.add(new int[] {neighborX, neighborY});

						// Distance From Start Node
						gcost = node.getCost() - (Math.abs(node.getX() - xDest) + Math.abs(node.getY() - yDest)) + 1;
						// Manhattan Distance to the Destination 
						hcost = Math.abs(neighborX - xDest) + Math.abs(neighborY - yDest);

						// Calculate Total Cost
						total_cost = gcost + hcost;
						// cost
						neighbor.setCost(total_cost);

						// Add Point to List
						priorityQueue.add(neighbor);
					}
				}
			}

			// No Path
			return null;
		}

		public static void pathfind(int[][] maze, int xStart, int yStart, int xDest, int yDest, List<int[]> path,
				List<int[]> searchArea) {
			// Conduct A Star
			Node node = AStarPath(maze, xStart, yStart, xDest, yDest, searchArea);

			if (node != null) {
				// Back Track Parent Nodes
				while (node.getParent() != null) {

					// Add Parent to Path
					path.add(new int[] {node.getX(), node.getY()});
					// Next Parent
					node = node.getParent();
				}
			}

			// Clear List
			priorityQueue.clear();
		}
}
