/* 
CS 1027B – Assignment 1 
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: January 31, 2025 
*/

public class Compound{
	// Instance Variables
	private Element[] elements;
	private int[] elementCount;
	
	// Constructor
	public Compound(PeriodicTable table, String[][] compoundArray) {
		// Initialize instance variables
		int compoundComponents = compoundArray.length;
		elements = new Element[compoundComponents];
		elementCount = new int[compoundComponents];
		
		// Populate instance variables for every element
		for(int i = 0; i < compoundArray.length; i++) {
			// String array to make it a bit easier to work with
			String[] elementInCompound = compoundArray[i];
			
			// Find the element and number of atoms given the line to work with
			Element element = table.getElement(elementInCompound[0]);
			int numOfAtoms = 1;
			if (elementInCompound.length == 2) {numOfAtoms = Integer.parseInt(elementInCompound[1]);}
			
			// Add the element and number of atoms to their respective arrays
			elements[i] = element;
			elementCount[i] = numOfAtoms;
		}
	} // end constructor
	
	// Get Bond Type
	public String getBondType() {
		// If not 2 elements, return null
		if (elements.length != 2) {return null;}
		else {			
			// Depending on first element
			switch(elements[0].getType()) {
				// If it's a metal
				case "Metal":
					// Check on the second element and give a result given its type
					if(elements[1].getType().equals("Nonmetal")) {return "ionic";}
					else if(elements[1].getType().equals("Metalloid")) {return "covalent";}
					else {return null;}
				
				// If it's a non-metal
				case "Nonmetal":
					// Check if the other one is a non-metal or not and give the corresponding results
					if(elements[1].getType().equals("Nonmetal")) {return "covalent";}
					else {return null;}
					
				// In any other case, return null
				default:
					return null;
			}
		}
	} // end of getBondType
	
	// To String
	public String toString(){
		// Initializing return string
		String bond = "";
		// Add each element's name and number of atoms to the string
		for (int i = 0; i < elements.length; i++) {
			String elementName = elements[i].getName();
			int numOfAtoms = elementCount[i];
			bond += elementName += ": " + numOfAtoms + "\n";
		}
		
		// Strip the last newline and return
		bond = bond.strip();
		return bond;
	} // end of toString
}