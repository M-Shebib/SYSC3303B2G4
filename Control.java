package project;

import java.util.*;
/**
 * class that is modified by the threads to allow them to work together
 */
public class Control {
	public int elevatorNumber; //integer to set the number of the elevator
	public List<Integer> inputFloor, destination, elevatorList; //List of aspects of the elevator
	public boolean dataIn; //boolean for Scheduler to run
	public boolean dataOut; //boolean for Elevator to run
	/**
	 * creates the control class and initializes the variables.
	 */
	public Control() {
		inputFloor = new ArrayList<Integer>();
		destination = new ArrayList<Integer>();
		elevatorList = new ArrayList<Integer>();
		dataIn = false;
		dataOut = false;
	}
}
