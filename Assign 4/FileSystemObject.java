/* 
CS 1027B – Assignment 4
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: March 25, 2025 
*/
public class FileSystemObject implements Comparable<FileSystemObject> {
	// Instance Variables
	private String name;
	private OrderedListADT<FileSystemObject> children;
	private FileSystemObject parent;
	private int id;
	
	// Constructor
	public FileSystemObject(String name, int id) {
		// Initialize variables
		this.name = name;
		this.id = id;
		
		// If this is a folder, also initialize children
		if (!this.isFile()) {
			this.children = new ArrayOrderedList<FileSystemObject>();
		}
	}
	
	
	// Getters
	public String getName() {return name;}
	public int getID() {return id;}
	public FileSystemObject getParent() {return parent;}
	public OrderedListADT<FileSystemObject> getChildren() {return children;}
	
	// Setters
	public void setName(String name) {this.name = name;}
	public void setParent(FileSystemObject parent) {this.parent = parent;}
	
	// Is FIle
	public boolean isFile() {
		// Check if this is a file or not
		return this instanceof ComputerFile; 
	}
	
	// Add Child
	public void addChild(FileSystemObject node) {
		// If this is a file, throw an exception
		if (this.isFile()){throw new DirectoryTreeException("Cannot store a file/folder within a file!");} 
		else {
			// Loop through each child to check if the node is already there
			ArrayIterator<FileSystemObject> childChecker = (ArrayIterator<FileSystemObject>) this.getChildren().iterator();
			while (childChecker.hasNext()) {
				FileSystemObject child = childChecker.next();
				// If it's already there, thrown a Directionary Tree Exception
				if(child.compareTo(node) == 0) {throw new DirectoryTreeException("Cannot have another file/folder with the same name!");} 
			}
			
			// Otherwise add the node
			this.children.add(node);
			node.setParent(this);
		}
	}
	
	// To String
	public String toString(){return this.name;}
	
	// Size
	/*QUARANTINE : Might be stopping CopyPath from working?*/
	public int size() {
		// If this is a file, return the ComputerFile override of this method
		if(this.isFile()) {
			return ((ComputerFile)this).size();
		}
		
		// Otherwise, use the helper method below
		else {
			int size = helperRecursion(this);
			return size;
		}
	}
	private int helperRecursion(FileSystemObject object) {
		// If object is a file, return the ComputerFile override of this method
		if(object.isFile()) {return ((ComputerFile)object).size();} 
		
		// Otherwise (which assumes the object is a folder), check each child for their sizes, then return the total sizes of the folder
		else {
			int subSize = 0;
			ArrayIterator<FileSystemObject> children = (ArrayIterator<FileSystemObject>) object.getChildren().iterator();
			// Loop through each child to recursively find their sizes
			while (children.hasNext()) {subSize += helperRecursion(children.next());}
			
			// Return size of folder
			return subSize;
		}
	}
	
	// Compare To
	public int compareTo (FileSystemObject other) {
		// If they are not the same class, compare such that the ComputerFile comes after the folder
		if (other.getClass() != this.getClass()) {
			if (this instanceof ComputerFile) {
				return 100;
			} else {return -100;}
		} 
		
		// If they are the same type of class, check their case-insensitive names
		else {return this.name.compareToIgnoreCase(other.getName());}
	}
}