/**
 * 
 */
package project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author shebib7290696
 *
 */
class ElevatorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("setting up");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Tearing Down");
	}

	/**
	 * Test method for {@link project.Elevator#Elevator(int)}.
	 */
	@Test
	void testElevator() {
		Elevator elevator = new Elevator(1);
		assertTrue(elevator.getElevatorUse().equals(ElevatorState.Idle));
	}

	/**
	 * Test method for {@link project.Elevator#moveUp()}.
	 */
	@Test
	void testMoveUp() {
		Elevator elevator = new Elevator(1);
		elevator.addDestinations(4);
		elevator.addDestinations(6);
		elevator.addDestinations(10);
		elevator.moveUp();
		assertEquals(elevator.getCurrentFloor(),2);
	}

	/**
	 * Test method for {@link project.Elevator#moveDown()}.
	 */
	@Test
	void testMoveDown() {
		Elevator elevator = new Elevator(1);
		elevator.addDestinations(4);
		elevator.addDestinations(6);
		elevator.addDestinations(10);
		elevator.setCurrentFloor(7);
		elevator.moveDown();
		assertEquals(elevator.getCurrentFloor(),6);
	}

	/**
	 * Test method for {@link project.Elevator#closestDestination()}.
	 */
	@Test
	void testClosestDestination() {
		Elevator elevator = new Elevator(1);
		elevator.addDestinations(4);
		elevator.addDestinations(6);
		elevator.addDestinations(10);
		assertEquals(elevator.closestDestination(),4);
	}

	/**
	 * Test method for {@link project.Elevator#sendData()}.
	 */
	@Test
	void testSendData() {
		Elevator elevator = new Elevator(1);
		elevator.addDestinations(4);
		elevator.addDestinations(6);
		elevator.addDestinations(10);
		elevator.sendData();
		assertNotNull(elevator.sendRecieveSocket);
	}

	/**
	 * Test method for {@link project.Elevator#getCurrentFloor()}.
	 */
	@Test
	void testGetCurrentFloor() {
		Elevator elevator = new Elevator(1);
		assertTrue(elevator.getCurrentFloor()==1);
	}

	/**
	 * Test method for {@link project.Elevator#setCurrentFloor(int)}.
	 */
	@Test
	void testSetCurrentFloor() {
		Elevator elevator = new Elevator(1);
		elevator.setCurrentFloor(5);
		assertTrue(elevator.getCurrentFloor()==5);
	}

	/**
	 * Test method for {@link project.Elevator#getElevatorUse()}.
	 */
	@Test
	void testGetElevatorUse() {
		Elevator elevator = new Elevator(1);
		assertTrue(elevator.getElevatorUse().equals(ElevatorState.Idle));
	}

	/**
	 * Test method for {@link project.Elevator#setElevatorUse(project.ElevatorState)}.
	 */
	@Test
	void testSetElevatorUse() {
		Elevator elevator = new Elevator(1);
		elevator.setElevatorUse(ElevatorState.Moving);
		assertTrue(elevator.getElevatorUse().equals(ElevatorState.Moving));
	}


}
