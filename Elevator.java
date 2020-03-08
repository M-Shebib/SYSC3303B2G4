package project;

import java.util.*;
import java.net.*;
import java.io.*;
import java.math.*;

/**
 * Thread for the elevator subsystem
 */

public class Elevator implements Runnable{
	DatagramPacket sendPacketE, recievePacket;
	DatagramSocket sendSocketE, recieveSocket;
	int elevatorPort;
	InetAddress elevatorAddress;
	private ElevatorState ElevatorUse; //The elevator currently in use
	private int elevatorNumber;
	private int currentFloor;
	private boolean dir;
	private String time;
	private List<Integer> inputFloor, destinations, elevatorList;
	private Scheduler scheduler;
	/*
	 * Initializes the Elevator thread and sets current floor to 1 and the direction to up and initializes ElevatorUse
	 * @param elevatorNumber the number of the elevator, so that there can be multiple elevators at once
	 * @param scheduler the Scheduler class that allows everything to communicate with one another
	 */
	public Elevator(int elevatorNumber, Scheduler scheduler) {
		setElevatorUse(ElevatorState.Idle);
		this.scheduler = scheduler;
		this.elevatorNumber = elevatorNumber;
		setCurrentFloor(1);
	}
	/**
	 * 
	 */
	public void moveUp() {
		if(!getElevatorUse().name().equals("Moving")) {
			setElevatorUse(ElevatorState.Moving);
		}
		setCurrentFloor(getCurrentFloor() + 1);
		System.out.println("Elevator system: Elevator moved up to 1 floor");
		sendData();
	}
	/**
	 * 
	 */
	public void moveDown() {
		if(!getElevatorUse().name().equals("Moving")) {
			setElevatorUse(ElevatorState.Moving);
		}
		setCurrentFloor(getCurrentFloor() - 1);
		System.out.println("Elevator system: Elevator moved down to 1 floor");
		sendData();
	}
	/**
	 * 
	 * @param destInput
	 */
	public void destinationInput(int destInput) {
		scheduler.moveElevatorToDestination(this.elevatorNumber, destInput);
	}
	/**
	 * runs the elevator thread
	 */
	public void run() {
			while(true) {
				receiveData();
				//Switches Elevator into ready to move state
				ElevatorUse.nextState(dir);
				//If the elevatorList isn't empty and dataOut is true			
				if(!scheduler.getElevatorList().isEmpty()) {
					//Sets Elevator to moving towards destination
					ElevatorUse.nextState(dir);
					
					do {
						if(dir) {//if dir = true then elevator is going up
							moveUp();
						}
						else {
							moveDown(); //if dir = false elevator is going down
						}
					}while(currentFloor!=closestDestination()); //Do this until the currentFloor is the destination
					
					//removes the initial floor that a passenger is picked up on
					scheduler.getElevatorList().remove(0);
					//removes the initial floor that was set to be the destination.
					scheduler.getElevatorList().remove(0);
					//Sets elevator back to idle.
					ElevatorUse.nextState(dir);
				}
				//sleeps if the conditions aren't met.
				try {
					Thread.sleep(200);
				}catch (InterruptedException e) {
					
				}
			}
			
		}
		
	/**
	 * 
	 */
	public synchronized void receiveData() {
		byte data[] = new byte[250];
		//datagramPacket that is meant to receive the socket
		recievePacket = new DatagramPacket(data, data.length);
		System.out.print("Elevator: Waiting for input Packet. \n");
		try {
			System.out.println("Waiting...");
			recieveSocket.receive(recievePacket);
		}catch(IOException e){
			System.out.print("IO Exception: Likely:");
			System.out.print("Recieve Socket Timed Out.\n"+ e);
			e.printStackTrace();
			System.exit(1);
		}
		
		//Process datagram recieved
		System.out.println("Elevator: Packet Recieved :");
		System.out.println("From host: "+recievePacket.getAddress());
		System.out.println("Host port: "+ recievePacket.getPort());
		int len = recievePacket.getLength();
		
		String recieved = new String(data,0,len);
		/*
		 * checks if there is an address and port associated with the elevator
		 */
		if(elevatorAddress.equals(null)) {
			elevatorAddress = recievePacket.getAddress();
			elevatorPort = recievePacket.getPort();
		}
		else {
		}
		String[] schedulerData = recieved.split(",");
		time = schedulerData[0];
		destinations.add(Integer.parseInt(schedulerData[1]));
		dir = Boolean.parseBoolean(schedulerData[2]);
		
		
	}
	/**
	 * 
	 * @return
	 */
	public int closestDestination() {
		int closeDest=1000;
		for(int i = 0; i<destinations.size(); i++) {
			if((Math.abs(currentFloor-destinations.get(i)))>(Math.abs(currentFloor-closeDest))) {
				closeDest = destinations.get(i);
			}
		}
		return closeDest;
		
	}
	public synchronized void sendData() {
		String eData = new String("E," + time + "," + getCurrentFloor() + "," + closestDestination() + "," + elevatorNumber);
		byte[] eDataByte= eData.getBytes();
		sendPacketE=new DatagramPacket(eDataByte,eDataByte.length,elevatorAddress,elevatorPort);
		try {
			sendSocketE.send(sendPacketE);
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println(" Elevator: Data recieved from scheduler, data from elevator "+ elevatorNumber +" sent to scheduler");
	}
	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	/**
	 * @param currentFloor the currentFloor to set
	 */
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	/**
	 * @return the elevatorUse
	 */
	public ElevatorState getElevatorUse() {
		return ElevatorUse;
	}
	/**
	 * @param elevatorUse the elevatorUse to set
	 */
	public void setElevatorUse(ElevatorState elevatorUse) {
		ElevatorUse = elevatorUse;
	}
	/**
	 * @return the inputFloor
	 */
	public List<Integer> getInputFloor() {
		return inputFloor;
	}
	/**
	 * @param inputFloor the inputFloor to set
	 */
	public void setInputFloor(List<Integer> inputFloor) {
		this.inputFloor = inputFloor;
	}
	/**
	 * @return the destinations
	 */
	public List<Integer> getDestinations() {
		return destinations;
	}
	/**
	 * @param destinations the destinations to set
	 */
	public void setDestinations(List<Integer> destinations) {
		this.destinations = destinations;
	}
	/**
	 * @return the elevatorList
	 */
	public List<Integer> getElevatorList() {
		return elevatorList;
	}
	/**
	 * @param elevatorList the elevatorList to set
	 */
	public void setElevatorList(List<Integer> elevatorList) {
		this.elevatorList = elevatorList;
	}	
}
