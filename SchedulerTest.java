/**
 * 
 */
package project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author shebib7290696
 *
 */
class SchedulerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Next Test");
	}

	/**
	 * Test method for {@link project.Scheduler#Scheduler(int, int)}.
	 */
	@Test
	void testScheduler() {
		Scheduler scheduler = new Scheduler(5,5);
		assertNotNull(scheduler.receiveSocket);
	}

	/**
	 * Test method for {@link project.Scheduler#receiveData()}.
	 */
	@Test
	void testReceiveData() {
		Scheduler scheduler = new Scheduler(5,5);
		scheduler.receiveData();
		assertNotNull(scheduler.receivePacket);
	}

	/**
	 * Test method for {@link project.Scheduler#sendData()}.
	 */
	@Test
	void testSendData() {
		Scheduler scheduler = new Scheduler(5,5);
		scheduler.sendData();
		assertNotNull(scheduler.sendSocketE);
	}
	/**
	 * Test method for {@link project.Scheduler#setInputTime(double)}.
	 */
	@Test
	void testSetInputTime() {
		Scheduler scheduler = new Scheduler(5,5);
		scheduler.setInputTime(0.6);
		assertEquals(scheduler.getInputTime(),0.6);
	}

}
