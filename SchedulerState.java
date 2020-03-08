package project;
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
			else if(input) {
			return Input;
			}
			else {
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
			}else return Idle;
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
			return ElevatorDeparting;
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
			return null;
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
	};

	public SchedulerState nextState(boolean input, boolean Elevator, boolean Error) {
		// TODO Auto-generated method stub
		return null;
	}

	public SchedulerState nextState() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}