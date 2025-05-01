/* 
CS 1027B – Assignment 2
Name: Fardin Abbassi 
Student Number: 251446579 
Email: fabbassi@uwo.ca
Created: February 14, 2025 
*/

public class TrainCar{
	// Instance Variables
	private int weight;
	private String freight;
	
	// Constructor
	public TrainCar(int weight, String freight) {
		this.weight = weight;
		this.freight = freight;
	}

	// Getters
	public int getWeight() {return weight;}
	public String getFreight() {return freight;}

	// Setters
	public void setWeight(int weight) {this.weight = weight;}
	public void setFreight(String freight) {this.freight = freight;}
	
	// To String
	public String toString() {return "<" + freight + ", " + weight + ">";}

	// Connection Check
	public boolean canConnect (TrainCar other) {
		// Check if the two train cars have the same weight or the same type of material
		boolean sameWeight = this.weight == other.weight;
		boolean sameFreight = this.freight.equals(other.freight);
		
		// Return if it can connect based on the two criteria
		return sameWeight || sameFreight;
	}

	// Equality Check
	public boolean equals(TrainCar other) {
		// Initiating variables
		boolean sameWeight, sameFreight, bothTrainCars;
		
		// Check if they have the same weight, freight and are both a TrainCar instance
		sameWeight = this.weight == other.weight;
		sameFreight = this.freight.equals(other.freight);
		bothTrainCars = (this.getClass().getName().equals("TrainCar")) && (other.getClass().getName().equals("TrainCar"));
		
		// Return if they are equal based on the three criteria
		return sameWeight && sameFreight && bothTrainCars;
	}
}