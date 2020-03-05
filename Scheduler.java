
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
/**
 * This thread is for the floor subsystem which knows what the initial floor is
 * as well as reads to determine the target floor.
 */
public class Floor implements Runnable{
	
	public BufferedReader input;
	public String instructions;
	public int currentTime, inputTime;
	public int floorNumber;
	public List<Integer> inputFloors;
	public Scheduler scheduler;
	public int dir;
	/**
	 * Creates an instance of the floor thread connected to the control.
	 * As well as attempts to read a file that has the inputs.
	 */
	public Floor(Scheduler scheduler, String inputFile) {
		this.scheduler = scheduler;
		try {
			input = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Takes the data held in the input buffered reader and separates it into values that
	 * are able to be used by the scheduler and Elevator.
	 */
	public void readData() {
		String[] data = instructions.split(",");
		String[]timeTemp = data[0].split("[:\\.]");
		inputTime=Integer.parseInt(timeTemp[0])*3600000+Integer.parseInt(timeTemp[1])*60000+Integer.parseInt(timeTemp[2])*1000+Integer.parseInt(timeTemp[3]);
		if (currentTime > inputTime){
			System.out.println("Floor subsystem: Input occurred before the previous input");
			System.exit(1);
		}
		if(currentTime==0) {
			currentTime=inputTime;
		}
		floorNumber = Integer.parseInt(data[1]);
		dir = Integer.parseInt(data[2]);
		
	}
	/**
	 * runs the floor thread
	 */
	public void run() {
		try {
			 
			//If there is still a line of buffered reader with instructions execute read data
			while((instructions = input.readLine()) !=null) {
				if (instructions.matches("\\d{2}:\\d{2}:\\d{2}:\\d{2},\\d{2},\\d{1}")) {
					readData();
					//When all of the instructions are read there is a pause and then the floor number
					//and destination are added to control system.
					try {
						Thread.sleep(inputTime-currentTime);
						System.out.println("Floor Subsystem: input recieved after "+ (inputTime-currentTime)+" ms, sending data to scheduler");
						currentTime=inputTime;
						scheduler.inputFloors.add(floorNumber);
					}
					catch (InterruptedException e) {
					
					}
				}
				else {
					System.out.println("Floor subsystem: invalid inputfile format");
					break;
				}
			}
		}
		catch (IOException e) {
				
		}	
	}
}
