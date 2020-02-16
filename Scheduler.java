import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Subsystem that communicates with the floors and elevator through the control
 *
 */
public class Scheduler {
	public int[] floors;
	public double inputTime;
	public List<Integer> inputFloors, destinations, elevatorList; //List of aspects of the elevator
	public boolean dataIn, dataOut;
	
	/**
	 * Simple constructor which connects the scheduler with the controller
	 * @param control is a class which allows for the three threads to communicate
	 */
	public Scheduler() {
		destinations = new ArrayList<Integer>();
		elevatorList = new ArrayList<Integer>();
		inputFloors = new ArrayList<Integer>();
		dataIn = false;
		dataOut = false;
	}
	/**
	 * Confirms that data has been received from floor and sets data out to true.
	 * This allows the elevator thread to run properly.
	 * 
	 * @param inputFloor the floor that the elevator is on when it receives the command
	 * @param destination the floor that the elevator will be going towards.
	 */
	public synchronized void receiveData(int inputFloor, int destination) {
		System.out.print("Data recieved from floor " + inputFloor + " to bring passenger to floor " + destination + "\n");
		dataOut = true;
		elevatorList.add(inputFloor);
		elevatorList.add(destination);
		inputFloors.remove(0);
		destinations.remove(0);
	}
	
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler();
		Thread elevator = new Thread(new Elevator(1, scheduler), "elevator1");
		Thread floor = new Thread(new Floor(scheduler, "ElevatorData.txt"), "floorsystem");
		floor.start();
		elevator.start();
	}
}
