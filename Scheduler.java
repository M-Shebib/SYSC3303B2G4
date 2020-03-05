import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Subsystem that communicates with the floors and elevator through the control
 *
 */
public class Scheduler {
	public List<Integer> floors;
	public List<Elevator> elevators;
	public double inputTime;
	public List<Integer> inputFloors, destinations, elevatorList; //List of aspects of the elevator
	public boolean dataIn, dataOut;
	private boolean elevatorFound;
	
	/**
	 * Simple constructor which connects the scheduler with the controller
	 * @param control is a class which allows for the three threads to communicate
	 */
	public Scheduler(int numOfElev, int numOfFloors) {
		destinations = new ArrayList<Integer>();
		elevatorList = new ArrayList<Integer>();
		inputFloors = new ArrayList<Integer>();
		elevators = new ArrayList<Elevator>();
		for (int i = 0; i < numOfElev; i++) {
			Elevator temp = new Elevator(i, this);
			elevators.add(temp);
		}
		floors = new ArrayList<Integer>();
		for (int j = 0; j < numOfFloors; j++) {
			floors.add(j);
		}
		dataIn = false;
		dataOut = false;
	}
	/**
	 * 
	 * Confirms that data has been received from floor and sets data out to true.
	 * This allows the elevator thread to run properly.
	 * 
	 * @param inputFloor the floor that the elevator is on when it receives the command
	 * @param destination the floor that the elevator will be going towards.
	 */
	public synchronized void receiveData(int inputFloor) {
		System.out.print("Scheduler: Data recieved from floor " + inputFloor + "\n");
		dataOut = true;
		elevatorList.add(inputFloor);
		inputFloors.remove(0);
		destinations.remove(0);
		moveElevator(inputFloor);
	}
	public void moveElevator(int inputFloor) {
		for (int i = 0; i < elevators.size(); i++) {
			while (!elevatorFound) {
				if (elevators.get(i).ElevatorUse == ElevatorState.Idle) {
					while (elevators.get(i).currentFloor != inputFloor){
						if (elevators.get(i).currentFloor < inputFloor) {
							elevators.get(i).moveUp();
						}
						if (elevators.get(i).currentFloor > inputFloor) {
							elevators.get(i).moveDown();
						}
					}
					elevatorFound = true;
					System.out.println("Scheduler: Elevator " + (i+1) + " moved to floor " + inputFloor);
				}
			}
		}
	}
	
	public synchronized void moveElevatorToDestination(int elevatorNumber, int destination) {
		if (elevators.get(elevatorNumber).currentFloor < destination) {
			elevators.get(elevatorNumber).moveUp();
		}
		if (elevators.get(elevatorNumber).currentFloor > destination) {
			elevators.get(elevatorNumber).moveDown();
		}
	}
	
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler(1, 30);
		Thread elevator = new Thread(new Elevator(1, scheduler), "elevator1");
		Thread floor = new Thread(new Floor(scheduler, "ElevatorData.txt"), "floorsystem");
		floor.start();
		elevator.start();
	}
}
