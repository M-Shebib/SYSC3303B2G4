import java.util.List;

/**
 * Thread for the elevator subsystem
 */

public class Elevator implements Runnable{
	
	public ElevatorState ElevatorUse; //The elevator currently in use
	public int[] destination;
	public int elevatorNumber;
	public int currentFloor;
	public List<Integer> inputFloor, destinations, elevatorList;
	public Scheduler scheduler;
	/*
	 * Initializes the Elevator thread and sets current floor to 1 and the direction to up and initializes ElevatorUse
	 * @param elevatorNumber the number of the elevator, so that there can be multiple elevators at once
	 * @param scheduler the Scheduler class that allows everything to communicate with one another
	 */
	public Elevator(int elevatorNumber, Scheduler scheduler) {
		ElevatorUse = ElevatorState.Idle;
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
	public void moveUp() {
		currentFloor ++;
		System.out.println("Elevator system: Elevator moved up to 1 floor");
	}
	public void moveDown() {
		currentFloor --;
		System.out.println("Elevator system: Elevator moved down to 1 floor");
	}
	public void destinationInput(int destInput) {
		scheduler.moveElevatorToDestination(this.elevatorNumber, destInput);
	}
	/**
	 * runs the elevator thread
	 */
	public void run() {
		while(true) {
			
			
		}
		
	}
}
