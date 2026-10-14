/* 
CS 1027B – Assignment 1 
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: January 26, 2025 
*/

public class PeriodicTable{
	// Instance Variables
	private Element[][] mainTable;
	private Element[] lanthanides;
	private Element[] actinides;
	
	// Constructor
	public PeriodicTable(String filename){
		mainTable = new Element[7][18];
		lanthanides = new Element[15];
		actinides = new Element[15];
		loadData(filename);
	}
	
	// Load Data
	// Load Data
	public void loadData(String filename) {
		// Preparing text file reader
		TextFileReader tfr = new TextFileReader(filename);
		
		
		// Initializing the first line (used to check type later) and the string array for its splits
		String[] checks = tfr.readString().split(",");
		String line;
		String[] splits;
		
		// Initializing counts for lanthanides and actinides
		int lanthinideCount = 0;
		int actinideCount = 0;		
		
		// While there's still data to be read
		while (!tfr.endOfFile()) {
			// Loading the next line and splitting it
			line = tfr.readString();
			splits = line.split(",");
			
			// Initializing element information
			int atomicNum = Integer.parseInt(splits[0]);
			float weight = Float.parseFloat(splits[3]);
			String symbol = splits[2];
			String name = splits[1];
			String state = splits[8];
			String type = splits[15];
			for (int i = 12; i < 15; i++) {
				if (splits[i].equals("yes")) {
					type = checks[i];
					break;
				}
			}

			// Initializing period/group information
			int period = Integer.parseInt(splits[7]);
			String group = splits[8];
			int atomicGroup = 0;
			if (!splits[8].isEmpty()) {
				atomicGroup = Integer.parseInt(splits[8]);
			}

			// Initializing element based on previous information
			Element elementToAdd = new Element(atomicNum, weight, symbol, name, state, type);
			
			// Initializing lanthanide and actinide checks
			boolean isLanthanide = period == 6 && group.isBlank();
			boolean isActinide = period == 7 && group.isBlank();

			// Add element to the proper group based on the information given
			if (isLanthanide) {
				lanthanides[lanthinideCount] = elementToAdd;
				lanthinideCount++;
			} else if(isActinide) {
				actinides[actinideCount] = elementToAdd;
				actinideCount++;
			}else {
				mainTable[period-1][atomicGroup-1] = elementToAdd;
			}
		} // end of while loop
		
		// Closing the text file reader
		tfr.close();
	} // end of loadData
	
	// To String
	public String toString() {		
		// Initializing the string to return
		String pTable = "";
		
		// Main Table
		for (Element[] period: mainTable) {
			// For each period in the table
			for (Element e: period) {
				// If the element exists, indent and add the symbol
				if (e != null) {		
					int spaces = 4 - e.getSymbol().length();
					String elementRep = "";
					while (spaces > 0) {
						elementRep += " ";
						spaces--;
					}
					elementRep += e.getSymbol().stripTrailing();
					// More Efficient: elementRep = e.getSymbol().indent(4 - e.getSymbol().length()).stripTrailing();
					// Autograder is old and doesn't have the indent() function, PLEASE update it :)
					pTable += elementRep;		
				}
				// If it doesn't exist, just indent
				if (e == null) {
					pTable += "    ";
				}
			}
			// Break after reaching the end of the period
			pTable += "\n";
		}
		
		// Create a line seperation between main table and lanthanides/actinides and indent, then for each lanthanide
		pTable += "\n            ";
		for (Element e: lanthanides) {			
			// If the element exists, indent and add the symbol
			if (e != null) {
				int spaces = 4 - e.getSymbol().length();
				String elementRep = "";
				while (spaces > 0) {
					elementRep += " ";
					spaces--;
				}
				elementRep += e.getSymbol().stripTrailing();
				// More Efficient: elementRep = e.getSymbol().indent(4 - e.getSymbol().length()).stripTrailing();
				// Autograder is old and doesn't have the indent() function, PLEASE update it :)
				pTable += elementRep;	
			}
		}
		
		// Create a line seperation and indent, then for each actinide
		pTable += "\n            ";
		for (Element e: actinides) {
			// If the element exists, indent and add the symbol
			if (e != null) {
				int spaces = 4 - e.getSymbol().length();
				String elementRep = "";
				while (spaces > 0) {
					elementRep += " ";
					spaces--;
				}
				elementRep += e.getSymbol().stripTrailing();
				// More Efficient: elementRep = e.getSymbol().indent(4 - e.getSymbol().length()).stripTrailing();
				// Autograder is old and doesn't have the indent() function, PLEASE update it :)
				pTable += elementRep;
			}
		}
		// Return the string
		return pTable;
	} // end of toString
	
	// Getters
	// Element Getter
	public Element getElement(String sym) {
		// Check each period for the specified element
		for (Element[] period: mainTable) {
			for (Element e: period) {
				if (e != null) {
					if (e.getSymbol().equals(sym)) {return e;}
				}
			}
		}
		
		// Check the lanthanides for the specified element
		for (Element e: lanthanides) {
			if (e != null) {
				if (e.getSymbol().equals(sym)) {return e;}
			}
		}
		
		// Check the actinides for the specified element
		for (Element e: actinides) {
			if (e != null) {
				if (e.getSymbol().equals(sym)) {return e;}
			}
		}
		
		// If nothing was found, return null
		return null;
	}
	
	//
	// Getters
	// Get Period
	
	// Period/Group Getters
	public Element[] getPeriod(int per) {
		// If invalid input, return null
		if (per <= 0 || per >= 8) {return null;}
		else {
			// Find the number of non-null entries in the period
			int x = 0;
			for (Element e: mainTable[per-1]) {
				if (e != null) {x++;}
			}
			
			// Initialize the array given the number of non-null entries, then populate the array
			Element[] period = new Element[x];
			x = 0;
			for (Element e: mainTable[per-1]) {
				if (e != null) {
					period[x] = e;
					x++;
				}
			}
			return period;
		}
	} // end of getPeriod
	
	// Get Group
	public Element[] getGroup(int grp) {
		// If invalid input, return null
		if(grp <= 0 || grp >= 19) {return null;}
		else {
			// Find the number of non-null entries in the group
			int x = 0;			
			for (Element[] period: mainTable) {
				if (period[grp-1] != null) {
					x++;
				}
			}
			
			// Initialize the array given the number of non-null entries, then populate the array
			Element[] group = new Element[x];
			x = 0;
			for (Element[] period: mainTable) {
				if (period[grp-1] != null) {
					group[x] = period[grp-1];
					x++;
				}
			}
			return group;
		}
	} // end of getGroup
	
	// Lanthanide/Actinide Getters
	public Element[] getLanthanides() {return lanthanides;}
	public Element[] getActinides() {return actinides;}
}