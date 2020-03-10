
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;
/**
 * This thread is for the floor subsystem which knows what the initial floor is
 * as well as reads to determine the target floor.
 */
@SuppressWarnings("unused")//java.util.Arrays is suppressed
public class Floor implements Runnable{
	
	private BufferedReader input;
	private String instructions;
	private int currentTime, inputTime;
	private int floorNumber, destination;
	private List<Integer> inputFloors, destinations;
	private Scheduler scheduler;
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket receiveSocket , sendSocket;
	private InetAddress schedulerIP;
	private int schedulerPort = 400;
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
	public Floor() {
		try {
			schedulerIP = InetAddress.getLocalHost();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}
	      try {
		         sendSocket = new DatagramSocket();
		         
		      } catch (SocketException se) {
		         se.printStackTrace();
		         System.exit(1);
		      }
	      try {
				input = new BufferedReader(new FileReader("inputfile.txt"));
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
		destination = Integer.parseInt(data[2]);

	}
	
	public synchronized void sendData() {
		
		String sData = new String("," + instructions);
		byte sDataByte[] = sData.getBytes();
		sendPacket = new DatagramPacket(sDataByte, sDataByte.length,schedulerIP,schedulerPort);
		try {
			sendSocket.send(sendPacket);
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	

	/**
	 * runs the floor thread
	 */
	public void run() {
		try {
			 
			//If there is still a line of buffered reader with instructions execute read data
			while((instructions = input.readLine()) !=null) {
				if (instructions.matches("\\d{2}:\\d{2}:\\d{2}:\\d{2},\\d{1},\\d{1}")) {
					readData();
					//When all of the instructions are read there is a pause and then the floor number
					//and destination are added to control system.
					try {
						Thread.sleep(inputTime-currentTime);
						System.out.println("Floor Subsystem: input recieved after "+ (inputTime-currentTime)+" ms, sending data to scheduler");
						currentTime=inputTime;
						sendData();
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
	public static void main(String[] args) {
		Floor floor = new Floor();
		floor.run();
	}

}