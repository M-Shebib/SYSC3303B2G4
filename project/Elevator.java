package project;

import java.util.List;

/**
 * Thread for the elevator subsystem
 */

public class Elevator implements Runnable{
	
	private boolean dir; //private boolean for indicating whether the button on the elevator is for up or down. with True being up and False being down.
	private ElevatorState ElevatorUse; //The elevator currently in use
	private int[] destination;
	private int elevatorNumber;
	private int currentFloor;
	public List<Integer> inputFloor, destinations, elevatorList;
	private Scheduler scheduler;
	/*
	 * Initializes the Elevator thread and sets current floor to 1 and the direction to up and initializes ElevatorUse
	 * @param elevatorNumber the number of the elevator, so that there can be multiple elevators at once
	 * @param scheduler the Scheduler class that allows everything to communicate with one another
	 */
	public Elevator(int elevatorNumber, Scheduler scheduler) {
		ElevatorState ElevatorUse = ElevatorState.Idle;
		this.scheduler = scheduler;
		this.setElevatorNumber(elevatorNumber);
		currentFloor = 1;
		dir = true;
	}
	/**
	 * @return the elevatorNumber
	 */
	public int getElevatorNumber() {
		return elevatorNumber;
	}
	/**
	 * @return the destination
	 */
	public int[] getDestination() {
		return destination;
	}
	/**
	 * @param elevatorNumber the elevatorNumber to set
	 */
	public void setElevatorNumber(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(int[] destination) {
		this.destination = destination;
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
			//Determines based off of elevatorList which direction the elevator is going to be going
			if(currentFloor - scheduler.elevatorList.get(1) > 0) {
				dir = false;
			}
			else {
				dir = true;
			}
			//Switches Elevator into ready to move state
			ElevatorUse.nextState(dir);
			//If the elevatorList isn't empty and dataOut is true			
			if(!scheduler.elevatorList.isEmpty()) {
				//Sets Elevator to moving towards destination
				ElevatorUse.nextState(dir);
				//Prints that it has arrived at the destination
				goToDestination(scheduler.elevatorList.get(0), currentFloor, scheduler.elevatorList.get(1));
				//changes currentFloor to the floor that was the destination
				currentFloor = scheduler.elevatorList.get(1);
				//removes the initial floor that a passenger is picked up on
				scheduler.elevatorList.remove(0);
				//removes the initial floor that was set to be the destination.
				scheduler.elevatorList.remove(0);
				//Sets elevator back to idle.
				ElevatorUse.nextState(dir);
			}
			//sleeps if the conditions aren't met.
			try {
				Thread.sleep(200);
			}catch (InterruptedException e) {
				
			}
		}
		
	}
}
