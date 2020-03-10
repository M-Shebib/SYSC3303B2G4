Tamer Ibrahim
Jordan Peterkin
Benjamin Ransom
Mario Shebib


Project Iteration 3
SYSC 3303 B2

naming convention

-Elevator : This thread is named after the subsystem it controls which is the elevator subsystem which is focused on the movement of the elevator and it's position.

-ElevatorState : This is a enum file with the four states of the Elevator.

-ElevatorTest : This JUnit test is associated with the Elevator class and when completed will be used for that subsystem.

-Floor : This is a thread that is named after the fact that it is in charge of the Floor subsystem which reads and interprets the inputs of which floor is the destination as well as what is the time and current floor.

-FloorTest: This JUnit test is associated with the Floor class and when completed will be used for that subsystem.

-Scheduler : This thread is named for being in charge of the scheduler subsystem which communicates with the Floor and Elevator Subsystems to give them the necessary information to run properly.

-SchedulerState: This enum is named for it holding the states of the Scheduler.

-SchedulerTest: This JUnit test is associated with the Scheduler class and when completed will be used for that subsystem.
Set up Instructions.

1. Extract all files including this one from zip file SYS3303B2Group4_ProjectIteration3.zip

2. Open Eclipse, Ideally Eclipse Java 2019-06 that is running Java 1.8.0 Standard

3. Select "Open Projects from File System..." and click on SYS3303B2Group4_ProjectIteration3 as directory and press finish.

4. Run Test.java


Files/Folders in zip folder :

1) src/project

1.a)Elevator.java
1.b)ElevatorState.java
1.c)ElevatorTest.java
1.d)Floor.java
1.e)FloorTest.java
1.f)Scheduler.java
1.g)SchedulerState.java
1.h)SchedulerTest.java

2.ElvatorStateMachineDiagram.pdf

3.input.txt

4. README.txt

5. SchedulerStateMachineDiagram.pdf

6. UMLClassDiagram.pdf

Task Distribution :

Tamer Ibrahim : initial coder of Floor.java UDP
Jordan Peterkin : Coder to make classes comport with previous requirements, organizer of initial roles
Benjamin Ransom : initial coder of Scheduler.java UDP
Mario Shebib : initial UDP Elevator.java coder, review of other UDP with corrections, implementation of readData from Elevator into Scheduler.java, creation of JUnitTest files, conversion of SchedulerStateMachineDiagram to pdf, creator of README.txt
