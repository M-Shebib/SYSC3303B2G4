
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class ElevatorTest {
	FileWriter write, write2;
	@Test
	// demonstrates that the floor class recognizes invalid format for input files by giving a valid line to handle then an invalid line
	void testProperFormatInputFile() throws IOException {
		FileWriter write = new FileWriter("testElevatorFormat.txt", true);
		PrintWriter print_line = new PrintWriter(write);
		print_line.printf("14:15:20:20,04,06\n1,2,3,4");
		print_line.close();
		Thread floor = new Thread(new Floor(new Scheduler(), "testElevatorFormat.txt"), "floor");
		floor.start();
	}
	@Test
	// displays "Input occurred before the previous input" to demonstrate that an input happened after 
	void testTimeGreaterThanCurrentTime() throws IOException{
		FileWriter write2 = new FileWriter("testElevatorTime.txt", true);
		PrintWriter print_line2 = new PrintWriter(write2);
		print_line2.printf("14:15:20:20,04,06\n00:00:00:10,05,20");
		print_line2.close();
		Thread floor2 = new Thread(new Floor(new Scheduler(), "testElevatorTime.txt"), "floor2");
		floor2.start();
	}
}
