package project;
/**
 * Thread for the elevator subsystem
 */

public class Elevator implements Runnable{
	
	public int[] destination;
	public int elevatorNumber;
	public int currentFloor;
	public Scheduler scheduler;
	public Control control;
	/*
	 * Initiallizes the Elevator thread and sets currentfloor to 1
	 * @param elevatorNumber the number of the elevator, so that there can be multiple elevators at once
	 * @param control the Control class that allows everything to communicate with one another
	 */
	
	/**
	 * Initializes the Elevator thread and sets currentFloor to 1.
	 * @param elevatorNumber the number of the elevator, so that there can be multiple elevators at once
	 * @param control the Control class that allows everything to communicate with one another

	 */
	public Elevator(int elevatorNumber, Control control) {
		this.elevatorNumber = elevatorNumber;
		this.control = control;
		currentFloor = 1;
	}
	/**
	 * This method prints out an explenation of the movement of the elevator.
	 * 
	 * @param inputFloor The floor at which passengers are picked up 
	 * @param currentFloor the starting floor of the elevator
	 * @param destination The destination floor
	 */
	public void goToDestination(int inputFloor, int currentFloor, int destination) {
		System.out.println("Elevator moved from floor " + currentFloor + " and picked up a passenger on floor " + inputFloor + " then dropped the passenger off at floor " + destination);
	}
	/**
	 * runs the elevator thread
	 */
	public void run() {
		while(true) {
			//If the elevatorList isn't empty and dataOut is true
			if(!control.elevatorList.isEmpty() && control.dataOut) {
				//Prints that it has arrived at the destination
				goToDestination(control.elevatorList.get(0), currentFloor, control.elevatorList.get(1));
				//changes currentFloor to the floor that was the destination
				currentFloor = control.elevatorList.get(1);
				//removes the initial floor that a passenger is picked up on
				control.elevatorList.remove(0);
				//removes the initial floor that was set to be the destination.
				control.elevatorList.remove(0);
			}
			//sleeps if the conditions aren't met.
			try {
				Thread.sleep(200);
			}catch (InterruptedException e) {
				
			}
		}
	}
}
