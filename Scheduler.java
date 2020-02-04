package project;
/**
 * 
 * Subsystem that communicates with the floors and elevator through the control
 *
 */
public class Scheduler implements Runnable{
	public int[] floors;
	public double inputTime;
	public Control control;
	
	/**
	 * Simple constructor which connects the scheduler with the controller
	 * @param control is a class which allows for the three threads to communicate
	 */
	public Scheduler(Control control) {
		this.control = control;
	}
	/**
	 * Confirms that data has been received from floor and sets data out to true.
	 * This allows the elevator thread to run properly.
	 * 
	 * @param inputFloor the floor that the elevator is on when it receives the command
	 * @param destination the floor that the elevator will be going towards.
	 */
	public void recieveData(int inputFloor, int destination) {
		System.out.print("Data recieved from floor " + inputFloor + " to bring passenger to floor " + destination + "\n");
		control.dataOut = true;
	}
	
	public void run() {
		while (true) {
			//This if statement confirms that there is an entity in the elevator and that there has been data put in the floor
			if(!control.destination.isEmpty() && control.dataIn) {
				//prepares for the elevator thread to run and makes sure that the control is updated for the situation
				recieveData(control.inputFloor.get(0), control.destination.get(0));
				control.elevatorList.add(control.inputFloor.get(0));
				control.elevatorList.add(control.destination.get(0));
				control.inputFloor.remove(0);
				control.destination.remove(0);
			}
			//prevents unnecessary code execution when if statement isn't met.
			try {
				Thread.sleep(100);
			}catch (InterruptedException e) {
				
			}
		}
	}
}
