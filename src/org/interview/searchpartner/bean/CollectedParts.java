package org.interview.searchpartner.bean;

public class CollectedParts {
	
	private int noOfMachines = 0;
	private int noOfBolts = 0;
	
	
	public CollectedParts(int noOfMachines, int noOfBolts) {
		super();
		this.noOfMachines = noOfMachines;
		this.noOfBolts = noOfBolts;
	}
	public int getNoOfMachines() {
		return noOfMachines;
	}
	public void setNoOfMachines(int noOfMachines) {
		this.noOfMachines = noOfMachines;
	}
	public int getNoOfBolts() {
		return noOfBolts;
	}
	public void setNoOfBolts(int noOfBolts) {
		this.noOfBolts = noOfBolts;
	}
	
	

}
