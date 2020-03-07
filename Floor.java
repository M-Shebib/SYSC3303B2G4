
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
public class Floor implements Runnable{
	
	public BufferedReader input;
	public String instructions;
	public int currentTime, inputTime;
	public int floorNumber, destination;
	public List<Integer> inputFloors, destinations;
	public Scheduler scheduler;
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket receiveSocket , sendSocket;
	private InetAddress schedulerIP;
	private int schedulerPort = 420;
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
		         receiveSocket = new DatagramSocket();
		         
		      } catch (SocketException se) {
		         se.printStackTrace();
		         System.exit(1);
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
	

	/**
	 * runs the floor thread
	 */
	public void run() {
		try {
			 
			//If there is still a line of buffered reader with instructions execute read data
			while((instructions = input.readLine()) !=null) {
				if (instructions.matches("\\d{2}:\\d{2}:\\d{2}:\\d{2},\\d{2},\\d{2}")) {
					readData();
					//When all of the instructions are read there is a pause and then the floor number
					//and destination are added to control system.
					try {
						Thread.sleep(inputTime-currentTime);
						System.out.println("Floor Subsystem: input recieved after "+ (inputTime-currentTime)+" ms, sending data to scheduler");
						currentTime=inputTime;
						scheduler.inputFloors.add(floorNumber);
						scheduler.destinations.add(destination);
						scheduler.receiveData(floorNumber);
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
	public void sendData (byte[] data, InetAddress IP, int port) {
		
		sendPacket = new DatagramPacket(data, data.length, IP, schedulerPort);
		System.out.println("Elevator: Sending packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
		System.out.print("Containing: ");
		System.out.println(Arrays.toString(data)); // or could print "s"
		try {
			sendSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Elevator: Packet sent.\n");
	}
	
	public void receiveData() {
		byte data[] = new byte[100];
		receivePacket = new DatagramPacket(data, data.length);
		System.out.println("Elevator: Waiting for Packet.\n");

		try {
			
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			System.out.print("IO Exception: likely:");
			System.out.println("Receive Socket Timed Out.\n" + e);
			e.printStackTrace();
			System.exit(1);
		}
		// Process the received Datagram.
		System.out.println("Elevator: Packet received:");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		int len = receivePacket.getLength();
		System.out.println("Length: " + len);
		System.out.print("Containing: ");
		System.out.println(Arrays.toString(data) + "\n");
}
}
