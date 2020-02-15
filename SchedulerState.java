
/**
 * 
 * @author benra
 *
 */
public enum SchedulerState {

	Idle{//Starting state of the scheduler
		
		
		@Override
		public SchedulerState nextState(boolean input,boolean Elevator, boolean Error) {
			if(Error) {
				return FaultDetected;
			}
			If(input){
			return InputRecieved;
			}else{
			return ElevatorArrived;
			}
		}
		
	},
	Input{
		@Override
		public SchedulerState nextState(boolean input,boolean Elevator, boolean Error) {
		if(Error) {
		return FaultDetected;
		}
			if(Elevator) {
			return InputAndElevatorArrived;
			}else return idle;
		}
	},
	ElevatorArrived{
		@Override
		public SchedulerState nextState(boolean input,boolean Elevator, boolean Error) {
		if(Error) {
		return FaultDetected;
		}
		if(input) {
			return InputAndElevatorArrived;
		}else {
			reutrn ElevatorDeparting;
		}
		}
	},
	ElevatorDeparting{
		@Override
		public SchedulerState nextState(boolean input,boolean Elevator, boolean Error) {
		if(Error) {
		return FaultDetected;
		}if(input) {
			return InputAndElevatorDeparting;
		}else {
			return Idle;
		}
		}
	},
	FaultDetected{
		@Override
		public SchedulerState nextState() {
			//Will proceed to handle an error when implemented
		}
		
	},
	InputAndElevatorArrived(){
		@Override
		public SchedulerState nextState(boolean input, boolean Elevator,boolean Error) {
			if(Error) {
			return FaultDetected;
			}
			if(!input) {
				return ElevatorArrived;
			}else {
				return InputAndElevatorDeparting;
			}
		}
	},
	InputAndElevatorDeparting(){
		@Override
		public SchedulerState nextState(boolean input,boolean Elevator, boolean Error) {
			if(Error) {
			return FaultDetected;
			}
			if(!input) {
				return ElevatorDeparting;
			}else {
			return Input;
			}
		}
	}
	
	
}
