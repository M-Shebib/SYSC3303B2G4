package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * This thread is for the floor subsystem which knows what the initial floor is
 * as well as reads to determine the target floor.
 */
public class Floor implements Runnable{
	
	public Control control;
	public BufferedReader input;
	public String instructions;
	public int currentTime, inputTime;
	public int floorNumber, destination;
	/**
	 * Creates an instance of the floor thread connected to the control.
	 * As well as attempts to read a file that has the inputs.
	 * @param control This is the controller that connects the three threads
	 */
	public Floor(Control control) {
		try {
			input = new BufferedReader(new FileReader("ElevatorData.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.control = control;
	}
	/**
	 * Takes the data held in the input buffered reader and seperates it into values that
	 * are able to be used by the scheduler and Elevator.
	 */
	public void readData() {
		String[] data = instructions.split(",");
		String[]timeTemp = data[0].split("[:\\.]");
		inputTime=Integer.parseInt(timeTemp[0])*3600000+Integer.parseInt(timeTemp[1])*60000+Integer.parseInt(timeTemp[2])*1000+Integer.parseInt(timeTemp[3]);
		if(currentTime==0) {
			currentTime=inputTime;
		}
		floorNumber = Integer.parseInt(data[1]);
		destination = Integer.parseInt(data[2]);

		control.dataIn = true;
	}
	/**
	 * runs the floor thread
	 */
	public void run() {
		try {
			//If there is still a line of buffered reader with instructions execute read data
			while((instructions = input.readLine()) !=null) {
				readData();
			//When all of the instructions are read there is a pause and then the floor number
			//and destination are added to control system.
			try {
					Thread.sleep(inputTime-currentTime);
					System.out.println("input recieved after "+ (inputTime-currentTime)+" ms, sending data to scheduler in ");
					currentTime=inputTime;
					control.inputFloor.add(floorNumber);
					control.destination.add(destination);
			}
			catch (InterruptedException e) {
				
			}
			}
		} catch (IOException e) {
			
		}
	}
	
}
