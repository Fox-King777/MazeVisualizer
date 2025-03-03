# MazeVisualizer

## Description
The Maze Visualizer has three parts. 

First is the maze generator. This app will include 3 different maze generation algorithms: Random Kruskal’s, Random Prim’s, and Recursive division. Mazes will be modeled in a two-dimensional grid where each cell could be a start, end, wall, empty, or visited node. The goal of navigating through this maze is to find a passable path from start to the end. 

Second is the maze runner; This app implements Depth First Search, Breadth First Search, A Start Search pathfinding algorithms. Maze runners can only move north, south, east, and west.

Third, and most importantly, is the visualizer; This app contains buttons for each algorithm the user wants to visualize. The maze generator algorithms will generate a maze and display the final result. The maze running algorithms will show the real time search path the algorithms take to reach the end. Upon reaching the end, the visualizer will draw a final path to the end. This app also contains buttons for all users to choose the start and end cells as well as a way to manually generate a maze with their mouse.

## Usage
After running the main code, the visualizer will pop up, which will include configurations and statistics of the maze and runner.

### Configuration Options

* Maze Generator: User’s will be able to choose between the maze generating algorithms
* Manual Maze: User’s will be able to manually make their own wall mazes
* Starting point: User’s get to choose the starting point
* Ending Point: User’s get to choose ending point
* Runner: User’s will be able to choose between the pathfinding algorithms

## Demo
https://github.com/user-attachments/assets/d73f1da1-efe5-4112-9ed9-e2f059921c53

