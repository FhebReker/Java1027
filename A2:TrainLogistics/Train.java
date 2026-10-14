/* 
CS 1027B – Assignment 2
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: February 14, 2025 
*/

public class Train{
	// Instance Variables
	private DoubleNode<TrainCar> locomotive;
	private DoubleNode<TrainCar> caboose;
	
	// Constructor
	public Train() {
		locomotive = null;
		caboose = null;
	}
	
	// Getters
	public DoubleNode<TrainCar> getCaboose() {return caboose;}
	public DoubleNode<TrainCar> getLocomotive() {return locomotive;}
	
	// Add a Car
	public void addCar (TrainCar car) {
		// If train is null, make the train car both locomotive and caboose
		if (caboose == null) {
			locomotive = caboose = new DoubleNode<TrainCar>(car);
		}
		
		// Otherwise, try to connect to the cars already in the train
		else {
			DoubleNode<TrainCar> newTrainCar = new DoubleNode<TrainCar>(car);

			// Try to connect to the end
			if (car.canConnect(caboose.getElement())) {
				caboose.setNext(newTrainCar);
				newTrainCar.setPrevious(caboose);
				caboose = newTrainCar;
				return;
			}
			
			// Otherwise, loop through each train car to find a way to attach it to the train
			else {
				for(DoubleNode<TrainCar> tc = caboose.getPrevious(); tc.getPrevious() != null; tc = tc.getPrevious()) {
					if(car.canConnect(tc.getPrevious().getElement()) && car.canConnect(tc.getElement())) {
						tc.getPrevious().setNext(newTrainCar);
						newTrainCar.setPrevious(tc.getPrevious());
						
						tc.setPrevious(newTrainCar);
						newTrainCar.setNext(tc);
						return;
					}
				}
			}
			
			// If it can't be added, throw a TrainException
			throw new TrainException("The train car " + car + " couldn't be added");
		}
	}

	// Try to Add a Car
	public boolean tryAddCar(TrainCar car) {
		// Initializing boolean to return
		boolean works = true;
		
		// Try to add the car to the train, throw exception if it doesn't work properly, then return the resulting boolean
		try {addCar(car);}
		catch (Exception e){
			works = false;
		}		
		return works;
	}
	
	// Remove a Car
	public void removeCar(TrainCar car) {	
		// Check if the train has cars to remove
		if (caboose != null) {
			// Check through all nodes to see if the car is there
			for(DoubleNode<TrainCar> tc = caboose; tc != null; tc = tc.getPrevious()) {
				if(tc.getElement().equals(car)) {
					// Check if we're removing the locomotive
					if (tc == locomotive) {
						locomotive.getNext().setPrevious(null);
						locomotive = tc.getNext();
						return;
					}
					
					// Check if we're removing the caboose
					else if (tc == caboose) {
						caboose.getPrevious().setNext(null);
						caboose = tc.getPrevious();
						return;
					}
					
					// Otherwise, if the connected cars can match, then remove references to the train car 
					else if (tc.getPrevious().getElement().canConnect(tc.getNext().getElement())) {
						tc.getPrevious().setNext(tc.getNext());
						tc.getNext().setPrevious(tc.getPrevious());
						return;
					}
					
					// In any other case, they can't be handled safely, so throw an error
					else {
						throw new TrainException("The car " + car + " could not be removed");
					}
				}
			}
		}
		
		// If nothing else happens or if the train is not initialized yet, then it's likely not in the train, so throw an error
		throw new TrainException("The car " + car + " could not be removed");
	}

	// Try to Remove a Car
	public boolean tryRemoveCar(TrainCar car) {		
		// Initializing boolean to return
		boolean works = true;
		
		// Try to remove the car from the train, throw exception if it doesn't work properly, then return the resulting boolean
		try {removeCar(car);}
		catch (Exception e){
			works = false;
		}		
		return works;
	}
	
	// To String
	public String toString() {
		// Check if the train has any cars attached
		if (locomotive == null) {
			return "The train is empty.";
		}else {
			// Initialize the string to return
			String returnStr = "";		
			
			// In case the locomotive points to itself, just add the one element to the string
			if (locomotive.getNext() == locomotive) {
				// Add its value depending on if it's a Reefer or not
				if (locomotive.getElement() instanceof Reefer) {returnStr += (Reefer)locomotive.getElement();}
				else {returnStr += locomotive.getElement();}
				
				/*There's probably a more efficient way to do this, but it works for now*/
				
			}
			
			// Otherwise go through each train car and add it to the string
			else {
				for(DoubleNode<TrainCar> tc = locomotive; tc != null; tc = tc.getNext()) {
					// Add its value depending on if it's a Reefer or not
					if (tc.getElement() instanceof Reefer) {returnStr += (Reefer)tc.getElement() + ", ";}
					else {returnStr += tc.getElement() + ", ";}		
					
					/*There's probably a more efficient way to do this, but it works for now*/

				}
	
				// Format to remove the last comma and space
				returnStr = returnStr.substring(0, returnStr.length() - 2);
			}
			
			// Return the string of train cars
			return returnStr;
		}
	}
}