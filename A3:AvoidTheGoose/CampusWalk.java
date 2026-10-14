/* 
CS 1027B – Assignment 3
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: March 6, 2025 
*/
public class CampusWalk{
	// Instance variables
	private Map map;
	
	// Constructor
	public CampusWalk(String filename, boolean showMap) {
		// Try to initialize the map, catch any errors that happen on bad input
		try {map = new Map(filename);} 
		catch (Exception e) {System.err.println("Error occured: " + e);}
		
		// Show or hide map depending on parameter
		if (showMap) {map.showGUI();}
		else {map.hideGUI();}
	}
	
	// Neighbour Goose Count
	public int neighbourGooseCount (Hexagon cell) {
		// Initialize number of gooses
		int gooseCount = 0;
		
		// For each neighbour cell
		for (int i = 0; i<6; i++) {			
			// If the cell's neighbour is not null
			if (cell.getNeighbour(i) != null) {
				// Check if it's a goose cell, then increment accordingly
				if(cell.getNeighbour(i).isGooseCell()) {
					gooseCount++;
				}
			}
		}
		
		// Return the number of gooses counted
		return gooseCount;
	}
	
	// Find Best
	public Hexagon findBest (Hexagon cell) {
		// Initialize variables
		Hexagon[] neighbours = new Hexagon[6];
		Hexagon bestNeighbour = null;
		int priority = 6;
		
		// Loop through each neighbouring cell
		for (int i = 0; i < 6; i++) {
			// Initialize the current cell looked at
			Hexagon current = cell.getNeighbour(i);
			
			// If the current cell is in the map, check each condition
			/* I hate how it looks, but it works*/
			if(current != null) {
				// If the current cell is a goose cell, move onto the next one
				if(current.isGooseCell()) {neighbours[i] = null;}
				
				// Otherwise, if the current cell has too many neighbouring goose cells and isn't the end cell, move onto the next one
				else if (neighbourGooseCount(current) >= 3 && !current.isEnd()) {neighbours[i] = null;}
				
				// Otherwise, if the current cell is already marked, move onto the next one
				else if (current.isMarked()) {neighbours[i] = null;}
				
				// Otherwise, set the hexagon array at this index to this one
				else {
					neighbours[i] = current;
				}
			}
		}		
		
		// Loop from greatest to least index due to how conditions are specified
		for (int i = 5; i >= 0; i--) {
			// Check all cases for hexagons
			if (neighbours[i] instanceof Hexagon) {								
				// If the current hexagon is the end cell, set the best neighbour to this cell and exit the loop
				if(neighbours[i].isEnd()) {bestNeighbour = neighbours[i]; break;}
				
				// Otherwise, if the current hexagon is a book cell, set the best neighbour to this cell
				else if(neighbours[i].isBookCell()) {
					if (priority >= 1) {
						bestNeighbour = neighbours[i];
						priority = 1;
					}
				}
				
				// Otherwise, if the current hexagon is a grass cell
				else if(neighbours[i].isGrassCell()) {
					// If the best neighbour cell is non-null
					if (bestNeighbour != null) {
						// Check if its neighbour goose count is lower than or equal to the current best neighbour, or if it's not a grass cell
						if (neighbourGooseCount(neighbours[i]) <= neighbourGooseCount(bestNeighbour) || !bestNeighbour.isGrassCell()) {
							// If it's lower or equal priority, set the best neighbour cell to this one
							if (priority >= 2) {
								bestNeighbour = neighbours[i];
								priority = 2;
							}				
						}
					}
					// If the best neighbour cell is null, set the best neighbour cell to this cell
					else {
						bestNeighbour = neighbours[i];
						priority = 2;
					}
				}
				
				// Otherwise, if the current hexagon is a snow cell, if the current priority is not at this level yet, set the best neighbour to this cell
				else if (neighbours[i].isSnowCell()) {
					if (priority >= 3) {
						bestNeighbour = neighbours[i];
						priority = 3;
					}
				}
			} 
		}		
		
		// Return whatever the best neighbour cell is
		return bestNeighbour;
	}
	
	// Find Path
	public String findPath () {
		// Initializing variables
		String path = "";
		ArrayStack<Hexagon> stack = new ArrayStack<Hexagon>();
		boolean running = true;		
		
		// Push the starting hexagon onto the stack and mark it as in-stack
		stack.push(map.getStart());
		stack.peek().markInStack();
		
		// Loop until the stack is empty or an exit is found
		while(!stack.isEmpty() && running) {
			// Take the top element of the stack and add its ID
			Hexagon current = stack.peek();
			path += current.getID() + " ";

			// If the current hexagon is an end hexagon, end the loop
			if (current.isEnd()) {
				running = false;
				break;
			}
			
			// Find the next best hexagon from the current one
			Hexagon next = findBest(current);
			
			// If there isn't any good hexagon to go to, pop off the current one and mark it as off-stack
			if (next == null) {
				stack.pop();
				current.markOutStack();
			}
			
			// Otherwise, push the next hexagon onto the stack and mark it as in stack
			else {
				stack.push(next);
				next.markInStack();
			}
		}
		
		// If the loop ended by finding an end hexagon, return the path taken
		if (!running) {return path;}
		// Otherwise return dismay at lack of path
		else {return "No path found";}
	}
	
	// Exit
	public void exit () {map.exit();}
	
	
	public static void main(String[] args) { 
		Hexagon.TIME_DELAY = 100; // Change speed of animation. 
		String file = "map4.txt"; // Change when trying other maps. 
		CampusWalk walk = new CampusWalk(file, true); 
		String result = walk.findPath(); 
		System.out.println(result);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		walk.exit();
	} 
}