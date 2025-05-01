/* 
CS 1027B – Assignment 4
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: March 29, 2025 
*/
public class DirectoryTree{
	// Initializing Root
	private FileSystemObject root;

	// Constructor
	public DirectoryTree(FileSystemObject root) {this.root = root;}
	
	// Root Getter
	public FileSystemObject getRoot() {return root;}
	
	// Level
	public int level(FileSystemObject fso) {
		// Increment level number for each ancestor the node has
		int currentLvl = 0;
		FileSystemObject parent = fso.getParent();
		while (parent != null) {
			currentLvl++;
			parent = parent.getParent();
		}
		
		// Return node's level
		return currentLvl;
	}
	
	// Lowest Common Ancestor
	public FileSystemObject lca(FileSystemObject a, FileSystemObject b) {
		// If any of the file objects are null, return null
		if (a == null || b == null) {return null;}
		
		// If file a has a higher level, go up a's ancestry until they have the same level
		if (level(a) > level(b)) {
			FileSystemObject x = a.getParent();
			while (level(x) != level(b)) {x = x.getParent();}
			
			// If they are equal to one another, return the node(s)
			if (x == b) {return x;}
			
			// Find their parents
			FileSystemObject xParent = x.getParent();
			FileSystemObject bParent = b.getParent();
			
			// If their parents are the same, return the parent
			if (xParent == bParent) {return xParent;} 
			
			// Otherwise find the lowest common ancestor of the parents
			else {return lca(xParent, bParent);}
		}
		
		// If file b has a higher level, go up b's ancestry until they have the same level
		else if (level(a) < level(b)) {
			FileSystemObject x = b.getParent();
			while (level(x) != level(a)) {x = x.getParent();}
			
			// If they are equal to one another, return the node(s)
			if (a == x) {return x;}
			
			// Find their parents
			FileSystemObject aParent = a.getParent();
			FileSystemObject xParent = x.getParent();
			
			// If their parents are the same, return the parent
			if (xParent == aParent) {return xParent;} 

			// Otherwise find the lowest common ancestor of the parents
			else {return lca(aParent, xParent);}
		}
		
		else {
			// If they are equal to one another, return the node(s)
			if (a == b) {return a;}
			
			// Find their parents
			FileSystemObject aParent = a.getParent();
			FileSystemObject bParent = b.getParent();
			
			// If their parents are the same, return the parent
			if (aParent == bParent) {return aParent;} 

			// Otherwise find the lowest common ancestor of the parents
			else {return lca(aParent, bParent);}
		}
	}

	// Build Path
	public String buildPath(FileSystemObject a, FileSystemObject b) {
		// If they are in the same folder, just return b
		if (a.getParent() == b.getParent()) {return b.getName();} 
		else {
			// Initializing strings
			String string = "";
			String visibleString = "";
			
			// Initializing file objects
			FileSystemObject toAncestor = a;
			FileSystemObject visibleFile = b;
			
			// Add to the string each time it takes to get to the lca from a
			while (toAncestor != lca(a, b)) {
				string = "../" + string;
				toAncestor = toAncestor.getParent();
			}
			
			// Add each ancestor of b before the lca
			while(visibleFile != lca(a, b)) {
				visibleString = "/" + visibleFile + visibleString;
				visibleFile = visibleFile.getParent();
			}
			// Format the legible string, then add to the prior string
			visibleString = visibleString.replaceFirst("/", "");
			string += visibleString;
			
			// Return the path
			return string;
		}
	}
	
	// To String
	public String toString() {
		// Call the helper method
		return helperMethod("", root);
	}
	private String helperMethod(String stringSoFar, FileSystemObject fileOrFolder) {				
		// If not root, add spaces based on depth level, then add file/folder name
		if (fileOrFolder != root) {
			for (int i = level(fileOrFolder)-1; i > 0; i--) {
				stringSoFar += "  ";
			}
			stringSoFar += " - " + fileOrFolder.getName();
		}
		// Otherwise just add the name
		else {stringSoFar += fileOrFolder.getName();}
		
		// Move onto next line
		stringSoFar += "\n";

		// If not a file (therefore a folder), go though each child in the folder and add to the string
		if(!fileOrFolder.isFile()) {
			ArrayIterator<FileSystemObject> children = (ArrayIterator<FileSystemObject>) fileOrFolder.getChildren().iterator();
			while (children.hasNext()) {
				stringSoFar += helperMethod("", children.next());
			}
		}
		
		// Return the string
		return stringSoFar;
	}

	// CutPaste
	public void cutPaste(FileSystemObject f, FileSystemObject dest) {
		// If dest is a file, throw a directory tree exception
		if (dest.isFile()) {
			throw new DirectoryTreeException("Cannot store files or folders in a file");
		} 
		
		// Otherwise, if f is the root, throw a directory tree exception
		else if (f.equals(root)) {			
			throw new DirectoryTreeException("cannot remove/cut/move the root of the whole tree");
		} 
		
		// Otherwise, move f from its original position and under the dest folder
		else {
			f.getParent().getChildren().remove(f);
			dest.addChild(f);
		}
	}
	
	// CopyPaste
	public void copyPaste(FileSystemObject f, FileSystemObject dest) {
		// If dest is a file, throw a directory tree exception
		if (dest.isFile()) {
			throw new DirectoryTreeException("Cannot store files/folders in a file!");
		} 
		
		// Otherwise, make a copy using the helperMethod, then add to the dest folder
		else {
			FileSystemObject copy = helperMethod(f);
			dest.addChild(copy);
		}
	}
	private FileSystemObject helperMethod(FileSystemObject file) {
		// Initializing copy file
		FileSystemObject copyFile = new FileSystemObject(file.getName(), file.getID() + 100);
		
		// If this is a computer file, return it as such
		if (file instanceof ComputerFile) {
			copyFile = new ComputerFile(file.getName(), file.getID() + 100, ((ComputerFile)file).size());
			return copyFile;
		} 
		
		// Otherwise, loop through each of its children to add to the copy folder
		else {
			ArrayIterator<FileSystemObject> children = (ArrayIterator<FileSystemObject>) file.getChildren().iterator();
			while (children.hasNext()) {
				copyFile.getChildren().add(helperMethod(children.next()));
			}		
			
			// Return the copied folder
			return copyFile;
		}
	}
}