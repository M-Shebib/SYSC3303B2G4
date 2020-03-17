
import java.util.*;

/**
 * Thread for the elevator subsystem
 */

public class Elevator implements Runnable{
	
	
	
	private ElevatorState ElevatorUse; //The elevator currently in use
	private int elevatorNumber; //individual identifier of this elevator
	private int currentFloor; //current floor that the elevator is on
	private List<Integer> destinations;
	private int dir;
	/*
	 * Initializes the Elevator thread and sets current floor to 1 and the direction to up and initializes ElevatorUse
	 * @param elevatorNumber the number of the elevator, so that there can be multiple elevators at once
	 * @param scheduler the Scheduler class that allows everything to communicate with one another
	 */
	public Elevator(int elevatorNumber) {
		destinations = new ArrayList<Integer>();
		setElevatorUse(ElevatorState.Idle);
		this.elevatorNumber = elevatorNumber;
		setCurrentFloor(1);
		
	}
	/**
	 * moves the elevator up one floor and sends the updated data to the scheduler
	 */
	public void moveUp() {
		if(!getElevatorUse().name().equals("Moving")) {
			setElevatorUse(ElevatorState.Moving);
		}
		setCurrentFloor(getCurrentFloor() + 1);
		System.out.println("Elevator " + elevatorNumber + ": Elevator moved up to 1 floor");
	}
	/**
	 * moves the elevator down one floor and sends the updated data to the scheduler
	 */
	public void moveDown() {
		if(!getElevatorUse().name().equals("Moving")) {
			setElevatorUse(ElevatorState.Moving);
		}
		setCurrentFloor(getCurrentFloor() - 1);
		System.out.println("Elevator " + elevatorNumber + ": Elevator moved down to 1 floor");
	}
	
	public void destinationInElevator() {
		int numOfDests = (int)(Math.random() * ((1 - 0) + 1));
		for (int i = 1; i <= numOfDests; i++) {
			destinations.add((int)(Math.random() * ((22 - 1) + 1)) + 1);
		}
	}
	
	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	/**
	 * @param currentFloor the currentFloor to set
	 */
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	/**
	 * @return the elevatorUse
	 */
	public ElevatorState getElevatorUse() {
		return ElevatorUse;
	}
	/**
	 * @param elevatorUse the elevatorUse to set
	 */
	public void setElevatorUse(ElevatorState elevatorUse) {
		ElevatorUse = elevatorUse;
	}

	/**
	 * @param destinations the destinations to add to list
	 */
	public void addDestinations(Integer destination) {
		this.destinations.add(destination);
	}

	public int getElevatorNumber() {
		return elevatorNumber;
	}
	
	public void setDirection(int dir) {
		this.dir = dir;
	}
	
	public int closestDestination() {
		int closeDest=1000; //int is so high that the data will be replaced by a destination
		for(int i = 0; i < destinations.size(); i++) {//for the size of the destinations list
			if((Math.abs(currentFloor-destinations.get(i)))<(Math.abs(currentFloor-closeDest))) {//absolute difference means that if the current floor is closer to the destination it is true
				closeDest = destinations.get(i); //changes closeDest to be the destination that is being checked
			}
		}
		return closeDest;
	}
	
	public void run() {
		
	}
}
