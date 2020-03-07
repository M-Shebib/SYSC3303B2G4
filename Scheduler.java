
import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;
/**
 * 
 * Subsystem that communicates with the floors and elevator through the control
 *
 */
public class Scheduler {
	public DatagramPacket sendPacketE,sendPacketF, recievePacket;
	public DatagramSocket sendSocketE,sendSocketF, recieveSocket;
	public InetAddress elevatorAddress, floorAddress;
	public int elevatorPort, floorPort;
	public List<Integer> floors;
	public List<Elevator> elevators;
	public SchedulerState SchedulerUse;
	public double inputTime;
	public List<Integer> inputFloors, destinations, elevatorList; //List of aspects of the elevator
	public boolean dataIn, dataOut;
	
	/**
	 * Simple constructor which connects the scheduler with the controller
	 * @param control is a class which allows for the three threads to communicate
	 */
	public Scheduler() {
		try {
			sendSocketE = new DatagramSocket();
			sendSocketF = new DatagramSocket();
			recieveSocket= new DatagramSocket(5000);
		}catch(SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
		destinations = new ArrayList<Integer>();
		elevatorList = new ArrayList<Integer>();
		inputFloors = new ArrayList<Integer>();
		elevators = new ArrayList<Elevator>();
		int numOfElev = 0;
		for (int i = 0; i < numOfElev; i++) {
			Elevator temp = new Elevator(i, this);
			elevators.add(temp);
		}
		floors = new ArrayList<Integer>();
		int numOfFloors = 0;
		for (int j = 0; j < numOfFloors; j++) {
			floors.add(j);
		}
		dataIn = false;
		dataOut = false;

	}
	/**
	 * Confirms that data has been received from floor and sets data out to true.
	 * This allows the elevator thread to run properly.
	 * @param floorNumber 
	 * 
	 * @param inputFloor the floor that the elevator is on when it receives the command
	 * @param destination the floor that the elevator will be going towards.
	 */
	public synchronized void receiveData(int floorNumber) {
		byte data[] = new byte[250];
		recievePacket = new DatagramPacket(data, data.length);
		System.out.print("Scheduler: Waiting for input Packet. \n");
		try {
			System.out.print("Waiting...");
			recieveSocket.receive(recievePacket);
		}catch(IOException e){
			System.out.print("IO Exception: Likely:");
			System.out.print("Recieve Socket Timed Out.\n"+ e);
			e.printStackTrace();
			System.exit(1);
		}
		
		//Process datagram recieved
		System.out.println("Scheduler: Packet Recieved :");
		System.out.println("From host: "+recievePacket.getAddress());
		
		System.out.println("Host port: "+ recievePacket.getPort());
		int len = recievePacket.getLength();
		
		String recieved = new String(data,0,len);
		/*
		 * Proccess input here:
		 * steps: check if Elevat or or floor{
		 * if according address in here is null{
		 * set address and port 
		 * else process normally
		 */
		if(recieved.equals("E")) {
			if(elevatorAddress.equals(null)) {
				elevatorAddress = recievePacket.getAddress();
				elevatorPort = recievePacket.getPort();
			}else {
				//process recieved data from the elevator(destination floor)
			}
		}else {
			if(floorAddress.equals(null)) {
				floorAddress = recievePacket.getAddress();
				floorPort = recievePacket.getPort();
			}
			String[] floorData = recieved.split(",");
			String eData = new String(floorData[1]+","+floorData[2]+","+floorData[3]);
			byte[] eDataByte= eData.getBytes();
			sendPacketE=new DatagramPacket(eDataByte,eDataByte.length,elevatorAddress,elevatorPort);
			try {
				sendSocketE.send(sendPacketE);
			}catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println(eData+" Scheduler: Data recieved from floor"+eData+" and sent to elevator");
		}
		
		


	}
	
	public void run() {
		
		while(true) {
			receiveData(elevatorPort);
		}
	}
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler();

		scheduler.run();
	}
	public void moveElevatorToDestination(int elevatorNumber, int destInput) {
		// TODO Auto-generated method stub
		
	}
	public void receiveData1(int floorNumber) {
		// TODO Auto-generated method stub
		
	}
}
