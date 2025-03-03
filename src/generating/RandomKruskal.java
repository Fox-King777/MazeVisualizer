package generating;

import java.util.*;

import visualization.Node;

public class RandomKruskal {
	private static class Edge {
		Node middle;
		int start;
		int end;

		public Edge(Node middle, int start, int end) {
			this.middle = middle;
			this.start = start;
			this.end = end;
		}
	}

	private static class Graph {
		int nodes;
		ArrayList<Edge> allEdges = new ArrayList<>();

		Graph(int vertices) {
			this.nodes = vertices;
		}

		private void addEdge(Node middle, int start, int end) {
			Edge edge = new Edge(middle, start, end);
			// Add to Total Edges
			allEdges.add(edge);
		}

		private void KruskalTree(int[][] maze) {
			Random random = new Random();

			// Set of Parent Connections
			int[] parent = new int[nodes];

			// Make Set of Nodes
			makeSet(parent);

			int index = 0;

			while (index < nodes - 1) {
				// Get Random Edge From Graph
				Edge edge = allEdges.remove(random.nextInt(allEdges.size()));

				// Check if Edge Creates a Cycle
				int xSet = find(parent, edge.start);
				int ySet = find(parent, edge.end);

				if (xSet != ySet) {
					// Add Edge to Maze
					maze[edge.middle.getY()][edge.middle.getX()] = 0;

					// Join Nodes in Set
					union(parent, xSet, ySet);

					index++;
				}
			}
		}

		private void makeSet(int[] parent) {
			// Create Set of Unconnected Nodes
			for (int i = 0; i < nodes; i++) {
				parent[i] = i;
			}
		}

		private int find(int[] parent, int vertex) {
			// Chain of Parent Pointers
			if (parent[vertex] != vertex) {

				// Find Parent of Vertex
				return find(parent, parent[vertex]);

			} else {
				// Parent Found
				return vertex;
			}
		}

		// Connect X And Y
		private void union(int[] parent, int x, int y) {
			// Find Parent of X
			int xParent = find(parent, x);

			// Find Parent of Y
			int yParent = find(parent, y);

			// Set X as Parent of Y
			parent[yParent] = xParent;
		}

	}

	// Set Entire Maze to Walls
	private static void WallMaze(int[][] maze, int x, int y) {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				maze[i][j] = 1;
			}
		}
	}

	// Create Cells
	private static void CreateNodes(int[][] maze, int x, int y) {
		for (int i = 1; i < y; i += 2) {
			for (int j = 1; j < x; j += 2) {
				maze[i][j] = 0;
			}
		}
	}

	public static void generate(int[][] maze, int x, int y) {
		// Block Entire Maze
		WallMaze(maze, x, y);

		int numNodes = ((maze.length - 1) / 2) * ((maze[0].length - 1) / 2);

		// New Graph
		Graph graph = new Graph(numNodes);

		int count = 0;

		for (int j = 1; j < maze.length - 1; j += 2) {
			for (int i = 1; i < maze[j].length - 1; i += 2) {

				if (i != maze[j].length - 2) {
					// Add Edge to Point on Right
					graph.addEdge(new Node(i + 1, j), count, count + 1);
				}

				if (j != maze.length - 2) {
					// Add Edge to Point Below
					graph.addEdge(new Node(i, j + 1), count, count + (maze[j].length - 1) / 2);
				}

				count++;
			}
		}

		// Create Cells
		CreateNodes(maze, x, y);

		// Produce Minimal Spanning Tree
		graph.KruskalTree(maze);
	}

}
