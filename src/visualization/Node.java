package visualization;

public class Node {
	// Coordinates
	private int x;
	private int y;
	
	// Previous node
	private Node parent;
	
	// Cost for heuristics
	private int cost = 0;

	public Node(int x, int y, Node parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	
	public Node(int x, int y, Node parent, int cost) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.cost = cost;
	}

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Output X Coordinate
	public int getX() {
		return this.x;
	}

	// Output Y Coordinate
	public int getY() {
		return this.y;
	}

	// Output Cost
	public int getCost() {
		return this.cost;
	}

	// Output Parent Node
	public Node getParent() {
		return this.parent;
	}

	// Set F Cost
	public void setCost(int cost) {
		this.cost = cost;
	}
}
