/* 
CS 1027B – Assignment 3
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: March 6, 2025 
*/

public class ArrayStack<T> implements StackADT<T>{
	// Instance Variables
	private T[] array;
	private int top;
	
	// Constructors
	public ArrayStack() {
		array = (T[]) new Object[10];
		top = 9;
	}
	public ArrayStack(int initCapacity) {
		array = (T[]) new Object[initCapacity];
		top = initCapacity - 1;
	}

	// Push
	public void push(T element) {
		boolean added = false;
		// If full, expand capacity using method
		if (top == -1) {this.expandCapacity();}
		
		// Add to right-most cell not already filled
		int index = array.length-1;
		while (!added && index > -1) {
			if (array[index] == null) {
				array[index] = element;				
				top--;
				added = true;
			}
			index--;
		}
	}

	// Pop
	public T pop() throws CollectionException {
		// If empty, throw collection exception
		if(this.isEmpty()) {throw new CollectionException("Stack is empty");}

		// Otherwise, pop the top element, move top to the right and return the element
		else {
			top++;
			T popped = array[top];
			array[top] = null;
			
			return popped;			
		}
	}

	// Peek
	public T peek() throws CollectionException {
		// If empty, throw collection exception
		if(this.isEmpty()) {throw new CollectionException("Stack is empty");}

		// Otherwise, peek at and return the top element
		else {
			T peeked = array[top+1];			
			return peeked;
		}
	}

	// Is Empty
	public boolean isEmpty() {
		// Initializing boolean
		boolean emptyCheck = true;
		
		// Loop through the entire array to find at least one element in the array
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {emptyCheck = false;}
		}		
		
		// return whether or not it's empty
		return emptyCheck;
	}

	// Size
	public int size() {
		// Initializing counting integer
		int count = 0;
		
		// Loop through the entire array to count non-null cells
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {count++;}
		}		
		
		// return non-null cell count
		return count;
	}
	
	// Getters
	public int getCapacity () {return array.length;}
	public int getTop() {return top;}
	
	// To String
	public String toString() {
		// Initializing string to return
		String str = "";
					
		// If empty, return feelings of emptiness
		if (this.isEmpty()) {str += "Empty Stack.";}
		else {
			// Loop through each non-null element and add it to the string
			for (int i = top+1; i < array.length; i++) {
				str += array[i] + ", ";}
			
			// Format the string by cutting off the last comma and space
			str = str.substring(0, str.length() - 2);			
		}
		
		// Return the string			
		return str;
	}

	// Expand Capacity
	private void expandCapacity() {
		// Initialze the expanded array, buffer and boolean to properly increase top
		T[] expandedArray;
		int buffer = array.length;
		boolean doubledOrIncremented;
		
		// If the current capacity is more than 15, add 10 more spaces and set boolean to increase top by 10
		if (getCapacity() > 15) {
			expandedArray = (T[]) new Object[getCapacity() + 10];
			buffer = 10;
			doubledOrIncremented = false;
		} 
		// Otherwise, double the capacity and set boolean to double top
		else {
			expandedArray = (T[]) new Object[getCapacity()*2];
			buffer = array.length;
			doubledOrIncremented = true;
		}

		// Copy all items from the array, then set the array to the expanded array
		for (int i = array.length-1; i > top; i--) {expandedArray[i + buffer] = array[i];}
		
		// Move top to leftmost null index accordingly and set the array to the expanded array
		if (doubledOrIncremented) {top += array.length;}
		else {top += 10;}
		array = expandedArray;
	}
}