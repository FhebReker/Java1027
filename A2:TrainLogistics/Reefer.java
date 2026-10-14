/* 
CS 1027B – Assignment 2
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: February 14, 2025 
*/

public class Reefer extends TrainCar{
	// Instance Variable
	private int temp;
	
	// Constructor
	public Reefer(int weight, int temp, String freight) {
		super(weight, freight);
		this.temp = temp;
	}

	// Temperature Getter/Setter
	public int getTemp() {return temp;}
	public void setTemp(int temp) {this.temp = temp;}
	
	// To String
	public String toString() {return "<" + this.getFreight() + ", " + this.getWeight() + ", " + temp + "C>";}
		
	// Connection Check
	public boolean canConnect (TrainCar other) {
		// Initiating variables
		boolean sameTrainCar, relativeReefers;

		// Use the TrainCar method to check for similarities in weight or freight
		sameTrainCar = super.canConnect(other);
		
		// If the other train car is a reefer, then check if it's within 5 degrees of this reefer
		if (other instanceof Reefer) {relativeReefers = temp-5 <= ((Reefer) other).temp && ((Reefer) other).temp <= temp+5;}
		else {relativeReefers = false;}
		
		// Return if it can connect based on the three criteria
		return sameTrainCar || relativeReefers;
	}

	// Equality Check
	public boolean equals(TrainCar other) {		
		// Initiating variables
		boolean sameWeight, sameFreight, bothReefers, sameTemp;
		
		// Check if they have the same weight, freight and are both a Reefer instance
		sameWeight = this.getWeight() == other.getWeight();
		sameFreight = this.getFreight().equals(other.getFreight());
		/* Theres definitely a better way to do this, but it works enough */
		bothReefers = (this.getClass().getName().equals("Reefer")) && (other.getClass().getName().equals("Reefer"));
		
		// If they are Reefers, check if they have the same temperature
		if(bothReefers) {sameTemp = this.getTemp() == ((Reefer)other).getTemp();}
		else {sameTemp = false;}
		
		// Return if they are equal based on the four criteria 
		return sameWeight && sameFreight && bothReefers && sameTemp;
	}
}