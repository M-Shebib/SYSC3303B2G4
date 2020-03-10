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
	DatagramPacket sendPacketE,sendPacketF, receivePacket;
	DatagramSocket sendSocketE,sendSocketF, receiveSocket;
	InetAddress elevatorAddress, floorAddress;
	int elevatorPort, floorPort;
	private List<Integer> floors;
	private List<Elevator> elevators;
	@SuppressWarnings("unused") //SchedulerUse isn't currently used
	private SchedulerState SchedulerUse;
	private double inputTime;
	private String time; //Time in the format of hh:mm:ss
	private List<Integer> inputFloors, destinations, elevatorList; //List of aspects of the elevator
	private boolean dir;
	private int currElev;
	
	/**
	 * Simple constructor which connects the scheduler with the controller
	 * @param control is a class which allows for the three threads to communicate
	 */
	public Scheduler(int numOfElev, int numOfFloors) {
		try {
			sendSocketE = new DatagramSocket();
			sendSocketF = new DatagramSocket();
			receiveSocket= new DatagramSocket(400);
		}catch(SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
		currElev = -1;
		destinations = new ArrayList<Integer>();
		elevatorList = new ArrayList<Integer>();
		inputFloors = new ArrayList<Integer>();
		elevators = new ArrayList<Elevator>();
		for (int i = 0; i < numOfElev; i++) {
			Elevator temp = new Elevator(i);
			elevators.add(temp);
		}
		floors = new ArrayList<Integer>();
		for (int j = 0; j < numOfFloors; j++) {
			floors.add(j);
		}


	}
	/*
	 * 
	 */
	public synchronized void receiveData() {
		byte data[] = new byte[250];
		receivePacket = new DatagramPacket(data, data.length);
		System.out.print("Scheduler: Waiting for input Packet. \n");
		try {
			System.out.println("Waiting...");
			receiveSocket.receive(receivePacket);
		}catch(IOException e){
			System.out.print("IO Exception: Likely:");
			System.out.print("receive Socket Timed Out.\n"+ e);
			e.printStackTrace();
			System.exit(1);
		}
		
		//Process datagram received
		System.out.println("Scheduler: Packet received :");
		System.out.println("From host: "+ receivePacket.getAddress());
		
		System.out.println("Host port: "+ receivePacket.getPort());
		int len = receivePacket.getLength();
		
		String received = new String(data,0,len);
		/*
		 * Process input here:
		 * steps: check if Elevator or floor{
		 * if according address in here is null{
		 * set address and port 
		 * else process normally
		 */
		if(received.charAt(0)==('D')) {
			System.out.println("Scheduler data recieved:"+received);
				elevatorAddress = receivePacket.getAddress();
				elevatorPort = receivePacket.getPort();
			
		}else if(received.charAt(0)==('E')) {
			System.out.println("Scheduler data recieved:"+received);
			String[] elevatorData = received.split(",");
			time = elevatorData[1];
			if(Integer.parseInt(elevatorData[2])<Integer.parseInt(elevatorData[3])) {
				dir = true;
			}
			else if(Integer.parseInt(elevatorData[2])==Integer.parseInt(elevatorData[3])) {
				destinations.remove(Integer.parseInt(elevatorData[3]));
			}
			else {
				dir = false;
			}
			currElev = Integer.parseInt(elevatorData[4]);
			
		}
		else {
			
				floorAddress = receivePacket.getAddress();
				floorPort = receivePacket.getPort();
			
			String[] floorData = received.split(",");
			System.out.println("Scheduler data recieved:"+received);
			String eData = new String(floorData[1]+","+floorData[2]+","+floorData[3]);
			byte[] eDataByte= eData.getBytes();
			sendPacketE=new DatagramPacket(eDataByte,eDataByte.length,elevatorAddress,elevatorPort);
			try {
				sendSocketE.send(sendPacketE);
			}
			catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println(floorData[1]+" Scheduler: Data received from floor"+floorData[2]+" and sent to elevator "+ currElev);
		}
		
		
		/*
		*dataOut = true;
		*elevatorList.add(inputFloor);
		*elevatorList.add(destination);
		*inputFloors.remove(0);
		*destinations.remove(0);
		*/

	}
	public synchronized void sendData() {
		String eData = new String(time + "," + destinations.get(0) + "," + dir);
		byte[] eDataByte= eData.getBytes();
		sendPacketE=new DatagramPacket(eDataByte,eDataByte.length,elevatorAddress,elevatorPort);
		try {
			sendSocketE.send(sendPacketE);
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println(" Scheduler: Data recieved from elevator, data from scheduler sent to scheduler");
	}
	
	public void run() {
		
		while(true) {
			
			receiveData();
			

		}
		
	}
	public InetAddress getFloorAddress() {
		return floorAddress;
	}
	public double getInputTime() {
		return inputTime;
	}
	public void setInputTime(double inputTime) {
		this.inputTime = inputTime;
	}
	public List<Integer> getInputFloors() {
		return inputFloors;
	}
	public void setInputFloors(List<Integer> inputFloors) {
		this.inputFloors = inputFloors;
	}
	public void setFloorAddress(InetAddress floorAddress) {
		this.floorAddress = floorAddress;
	}
	public List<Integer> getDestinations() {
		return destinations;
	}
	public void setDestinations(List<Integer> destinations) {
		this.destinations = destinations;
	}
	public List<Integer> getElevatorList() {
		return elevatorList;
	}
	public void setElevatorList(List<Integer> elevatorList) {
		this.elevatorList = elevatorList;
	}
	/**
	 * @param inputFloor the inputFloor to add to list
	 */
	public void addInputFloor(Integer inputFloor) {
		this.inputFloors.add(inputFloor);
	}
	/**
	 * @param destinations the destinations to add to list
	 */
	public void addDestinations(Integer destination) {
		this.destinations.add(destination);
	}
	
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler(2, 5);

		scheduler.run();
	}
}