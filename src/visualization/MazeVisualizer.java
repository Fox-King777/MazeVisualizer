package visualization;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

// Import Pathfinding Algorithms
import pathfinding.*;

// Import Maze Generation Algorithms
import generating.*;

@SuppressWarnings("serial")
public class MazeVisualizer extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	// Initialize Fonts
	private static final Font BUTTON_FONT = new Font("Monospaced", Font.BOLD, 15);

	// Initialize Button Color
	private static final Color GENERATOR_BUTTON_COLOR = new Color(208, 236, 247);
	private static final Color PATH_FIND_BUTTON_COLOR = new Color(255, 229, 204);
	private static final Color NODE_BUTTON_COLOR = new Color(229, 204, 255);

	// Initialize Square Color
	private static final Color PATH_COLOR = new Color(194, 124, 255);
	private static final Color WALL_COLOR = Color.BLACK;
	private static final Color START_COLOR = new Color(147, 76, 178);
	private static final Color DESTINATION_COLOR = Color.WHITE;

	// Maze Generation Buttons
	private JButton mGen = new JButton("Manual Generation");
	private JButton rDiv = new JButton("Recursive Division");
	private JButton rKruskal = new JButton("Randomized Kruskal`s");
	private JButton rPrim = new JButton("Randomized Prim`s");

	// Path Finding Buttons
	private JButton dfs = new JButton("Depth First Search");
	private JButton breadthFS = new JButton("Breadth First Search");
	private JButton aStar = new JButton("A Star");

	// Node Creation Buttons
	private JButton start = new JButton("Create Start Node");
	private JButton destination = new JButton("Create Destination Node");

	// Variable to Select Mouse Operation
	private int mouseOp = 3;

	// Toggle Manual Generation
	private int manualToggle = 1;

	// Store Old Start and Destination Coordinates
	private int startX = 0;
	private int startY = 0;
	private int destX = 0;
	private int destY = 0;

	// Stores if Start and Destination Nodes are Present
	private boolean isStart = false;
	private boolean isDest = false;
	
	// Stores a List of Coordinates in the Path
	// Each Coordinate is Stored in a Array of Size 2, [0] = x; [1] = y
	private List<int[]> path = new ArrayList<int[]>();
	
	// Stores a List of Visited Coordinates
	// Each Coordinate is Stored in a Array of Size 2, [0] = x; [1] = y
	private List<int[]> searchArea = new ArrayList<int[]>();

	private MazeVisualizer() {
		// Main Menu Window Settings
		setTitle("Maze Visualizer");
		setSize(1200, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		//setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("assets/icon.png")));

		// Mouse Event Settings
		addMouseListener(this);
		addMouseMotionListener(this);

		// Initialize Maze
		Maze.clearMaze();

		// Clear Path Squares
		Maze.clearPath();

		// Call Components Method
		Components();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Size of Square
		int square_size = 15;

		// Grid Start Coordinate
		int start_x = 300;
		int start_y = 50;

		// Draw Maze
		for (int r = 0; r < Maze.maze.length; ++r) {
			for (int c = 0; c < Maze.maze[r].length; ++c) {

				Color squareColor;

				// Wall
				if (Maze.maze[r][c] == 1) {
					squareColor = WALL_COLOR;
				}

				// Start Node
				else if (Maze.maze[r][c] == 2) {
					squareColor = START_COLOR;
				}

				// Destination Node
				else if (Maze.maze[r][c] == 9) {
					squareColor = DESTINATION_COLOR;
				}

				// Empty Square
				else {
					squareColor = Color.GRAY;
				}

				// Draw Square
				g.setColor(squareColor);
				g.fillRect(square_size * c + start_x, square_size * r + start_y, square_size, square_size);
			}
		}

		// Draw Search Area
		for (int p = 0; p < searchArea.size(); ++p) {
			int searchX = searchArea.get(p)[0];
			int searchY = searchArea.get(p)[1];

			// Uncover Start and Destination Node
			if (Maze.maze[searchY][searchX] != 2 && Maze.maze[searchY][searchX] != 9) {

				// Add Delay In Between Search Area Drawing
				try {
					Thread.sleep(7);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}

				g.setColor(new Color(255, 153, Math.min(255, p / 3)));
				g.fillRect(square_size * searchX + start_x, square_size * searchY + start_y, square_size, square_size);
			}
		}

		// Draw Path From Start to Destination
		for (int p = path.size() - 1; p >= 0; --p) {
			int pathX = path.get(p)[0];
			int pathY = path.get(p)[1];

			// Uncover Start and Destination Node
			if (Maze.maze[pathY][pathX] != 2 && Maze.maze[pathY][pathX] != 9) {

				// Add Delay In Between Path Drawing
				try {
					Thread.sleep(7);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}

				g.setColor(PATH_COLOR);
				g.fillRect(square_size * pathX + start_x, square_size * pathY + start_y, square_size, square_size);
			}
		}

		clearSearchLists();
	}

	private void Components() {
		// Recursive Backtracking Button Settings
		mGen.setBounds(25, 45, 250, 40);
		SetButtonSettings(mGen);
		SetButtonColor(mGen, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Recursive Division Button Settings
		rDiv.setBounds(25, 95, 250, 40);
		SetButtonSettings(rDiv);
		SetButtonColor(rDiv, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Randomized Kruskal Button Settings
		rKruskal.setBounds(25, 145, 250, 40);
		SetButtonSettings(rKruskal);
		SetButtonColor(rKruskal, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Randomized Prim Button Settings
		rPrim.setBounds(25, 195, 250, 40);
		SetButtonSettings(rPrim);
		SetButtonColor(rPrim, GENERATOR_BUTTON_COLOR, Color.BLACK);

		// Create Start Node Button Settings
		start.setBounds(25, 310, 250, 40);
		SetButtonSettings(start);
		SetButtonColor(start, NODE_BUTTON_COLOR, Color.BLACK);

		// Create Destination Node Button Settings
		destination.setBounds(25, 360, 250, 40);
		SetButtonSettings(destination);
		SetButtonColor(destination, NODE_BUTTON_COLOR, Color.BLACK);

		// Depth First Search Button Settings
		dfs.setBounds(25, 425, 250, 40);
		SetButtonSettings(dfs);
		SetButtonColor(dfs, PATH_FIND_BUTTON_COLOR, Color.BLACK);

		// Breadth First Search Button Settings
		breadthFS.setBounds(25, 475, 250, 40);
		SetButtonSettings(breadthFS);
		SetButtonColor(breadthFS, PATH_FIND_BUTTON_COLOR, Color.BLACK);

		// A Star Button Settings
		aStar.setBounds(25, 525, 250, 40);
		SetButtonSettings(aStar);
		SetButtonColor(aStar, PATH_FIND_BUTTON_COLOR, Color.BLACK);
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == mGen) {
			// Toggle Manual Generation On
			manualToggle = 1;

			// Set Mouse Operation to Wall
			mouseOp = 3;

			Maze.clearMaze();

			clearSearchLists();

			// Reset Start or Destination
			isStart = false;
			isDest = false;

			repaint();
		}

		if (event.getSource() == rDiv) {
			// Toggle Manual Generation Off
			manualToggle = 0;

			// Default Mouse Operation
			mouseOp = 0;

			Maze.clearMaze();

			clearSearchLists();

			// Reset Start or Destination
			isStart = false;
			isDest = false;

			RecursiveDivision.generate(Maze.maze, 1, 1, Maze.getWidth() - 2, Maze.getHeight() - 2);

			repaint();
		}

		if (event.getSource() == rKruskal) {
			// Toggle Manual Generation Off
			manualToggle = 0;

			// Default Mouse Operation
			mouseOp = 0;

			Maze.clearMaze();

			clearSearchLists();

			// Reset Start or Destination
			isStart = false;
			isDest = false;

			RandomKruskal.generate(Maze.maze, Maze.getWidth(), Maze.getHeight());

			repaint();
		}

		if (event.getSource() == rPrim) {
			// Toggle Manual Generation Off
			manualToggle = 0;

			// Default Mouse Operation
			mouseOp = 0;

			Maze.clearMaze();

			clearSearchLists();

			// Reset Start or Destination
			isStart = false;
			isDest = false;

			RandomPrim.generate(Maze.maze, Maze.getWidth(), Maze.getHeight());

			repaint();
		}

		if (event.getSource() == dfs) {
			if (isStart && isDest) {
				Maze.maze[startY][startX] = 0;

				// Clear Path Squares
				Maze.clearPath();

				clearSearchLists();

				DFS.pathfind(Maze.maze, startX, startY, path, searchArea);

				Maze.maze[startY][startX] = 2;

				repaint();
			}
		}

		if (event.getSource() == breadthFS) {
			if (isStart && isDest) {
				// Clear Path Squares
				Maze.clearPath();

				clearSearchLists();

				Maze.maze[startY][startX] = 3;

				BFS.pathfind(Maze.maze, startX, startY, path, searchArea);

				Maze.maze[destY][destX] = 9;
				Maze.maze[startY][startX] = 2;

				repaint();
			}
		}

		if (event.getSource() == aStar) {
			if (isStart && isDest) {
				// Clear Path Squares
				Maze.clearPath();

				clearSearchLists();

				AStar.pathfind(Maze.maze, startX, startY, destX, destY, path, searchArea);

				Maze.maze[startY][startX] = 2;

				repaint();
			}
		}

		if (event.getSource() == start) {
			// Set Mouse Operation to Start Node
			mouseOp = 1;
		}

		if (event.getSource() == destination) {
			// Set Mouse Operation to Destination Node
			mouseOp = 2;
		}
	}

	private void clearSearchLists() {
		// Clear Path List
		path.clear();

		// Clear Search Area List
		searchArea.clear();
	}
	
	private void CustomMaze(MouseEvent click) {

		if (SwingUtilities.isLeftMouseButton(click)) {

			// Get Coordinates of Mouse Click
			int xClick = click.getX();
			int yClick = click.getY();

			if (315 <= xClick && xClick <= 1150 && 65 <= yClick && yClick <= 650) {

				// Convert Coordinates to Array Index
				int xI = ((xClick - 315) / 15) + 1;
				int yI = ((yClick - 65) / 15) + 1;

				// Add Start Node
				if (mouseOp == 1 && (Maze.maze[yI][xI] == 0 || Maze.maze[yI][xI] == 3)) {
					if (isStart == true) {

						if (Maze.maze[startY][startX] == 2) {
							// Delete Old Start Node
							Maze.maze[startY][startX] = 0;
						}

					}

					// Store New Start Node Coordinates
					startX = xI;
					startY = yI;

					// Create New Start Node
					Maze.maze[yI][xI] = 2;
					isStart = true;

					// Set MouseOp to Make Walls
					mouseOp = 3;
				}

				// Add Destination Node
				else if (mouseOp == 2 && (Maze.maze[yI][xI] == 0 || Maze.maze[yI][xI] == 3)) {
					if (isDest == true) {

						if (Maze.maze[destY][destX] == 9) {
							// Delete Old Start Node
							Maze.maze[destY][destX] = 0;
						}

					}

					// Store New Destination Node Coordinates
					destX = xI;
					destY = yI;

					// Create New Destination Node
					Maze.maze[yI][xI] = 9;
					isDest = true;

					// Set MouseOp to Make Walls
					mouseOp = 3;

				}

				// Add Maze Walls
				else if (mouseOp == 3 && manualToggle == 1 && (Maze.maze[yI][xI] == 0 || Maze.maze[yI][xI] == 3)) {
					Maze.maze[yI][xI] = 1;
				}
			}

		} else if (SwingUtilities.isRightMouseButton(click)) {
			// Get Coordinates of Mouse Click
			int xClick = click.getX();
			int yClick = click.getY();

			if (315 <= xClick && xClick <= 1150 && 65 <= yClick && yClick <= 650) {
				// Convert Coordinates to Array Index
				int xI = ((xClick - 315) / 15) + 1;
				int yI = ((yClick - 65) / 15) + 1;

				// Remove Maze Walls
				if (manualToggle == 1 && Maze.maze[yI][xI] == 1) {
					Maze.maze[yI][xI] = 0;
				}

				// Remove Start Node
				else if (Maze.maze[yI][xI] == 2) {
					Maze.maze[yI][xI] = 0;

					isStart = false;
				}

				// Remove Destination Node
				else if (Maze.maze[yI][xI] == 9) {
					Maze.maze[yI][xI] = 0;

					isDest = false;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent click) {
		CustomMaze(click);
	}

	@Override
	public void mouseDragged(MouseEvent click) {
		CustomMaze(click);
	}

	@Override
	public void mouseReleased(MouseEvent click) {
		if (mouseOp != 0) {
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent click) {
	}

	@Override
	public void mousePressed(MouseEvent click) {
	}

	@Override
	public void mouseEntered(MouseEvent click) {
	}

	@Override
	public void mouseExited(MouseEvent click) {
	}

	private void SetButtonSettings(JButton button) {
		button.setFont(BUTTON_FONT);
		button.setBorderPainted(true);
		button.setFocusPainted(false);
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
	}

	private void SetButtonColor(JButton button, Color bg, Color fg) {
		button.setBackground(bg);
		button.setForeground(fg);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// Set UI to Cross Platform
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}

				MazeVisualizer view = new MazeVisualizer();
				view.setVisible(true);
			}
		});
	}
}
