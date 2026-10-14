/* 
CS 1027B – Assignment 4
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: March 25, 2025 
*/
public class ComputerFile extends FileSystemObject{
	// Instance Variable
	private int size;
	
	// Constructor
	public ComputerFile(String name, int id, int size) {
		super(name, id);
		this.size = size;
	}
	
	// Size
	public int size() {
		return size;
	}
}