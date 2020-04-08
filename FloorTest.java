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
class FloorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("start");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("next test");
	}
	/**
	 * Test method for {@link project.Floor#Floor()}.
	 */
	@Test
	void testFloor() {
		Floor floor = new Floor();
		assertNotNull(floor.sendSocket);
	}

	/**
	 * Test method for {@link project.Floor#readData()}.
	 */
	@Test
	void testReadData() {
		Floor floor = new Floor();
		floor.run();
		floor.readData();
		floor.sendData();
		assertNotNull(floor.sendPacket);
	}

	/**
	 * Test method for {@link project.Floor#sendData()}.
	 */
	@Test
	void testSendData() {
		Floor floor = new Floor();
		floor.run();
		floor.readData();
		floor.sendData();
		assertNotNull(floor.sendSocket);
	}

}
