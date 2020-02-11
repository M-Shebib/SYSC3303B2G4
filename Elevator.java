package project;

import java.util.List;

/**
 * Thread for the elevator subsystem
 */

public class Elevator implements Runnable{
	
	public int[] destination;
	public int elevatorNumber;
	public int currentFloor;
	public List<Integer> inputFloor, destinations, elevatorList;
	public Scheduler scheduler;
	/*
	 * Initializes the Elevator thread and sets current floor to 1
	 * @param elevatorNumber the number of the elevator, so that there can be multiple elevators at once
	 * @param control the Control class that allows everything to communicate with one another
	 */
	public Elevator(int elevatorNumber, Scheduler scheduler) {
		this.scheduler = scheduler;
		this.elevatorNumber = elevatorNumber;
		currentFloor = 1;
	}
	/**
	 * This method prints out an explanation of the movement of the elevator.
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
			if(!scheduler.elevatorList.isEmpty()) {
				//Prints that it has arrived at the destination
				goToDestination(scheduler.elevatorList.get(0), currentFloor, scheduler.elevatorList.get(1));
				//changes currentFloor to the floor that was the destination
				currentFloor = scheduler.elevatorList.get(1);
				//removes the initial floor that a passenger is picked up on
				scheduler.elevatorList.remove(0);
				//removes the initial floor that was set to be the destination.
				scheduler.elevatorList.remove(0);
			}
			//sleeps if the conditions aren't met.
			try {
				Thread.sleep(200);
			}catch (InterruptedException e) {
				
			}
		}
		
	}
}
