public enum ElevatorState {
	Idle { //The starting state of the Elevator
		
		/**
		 * 
		 * @param dir the boolean state of which direction the elevator is going
		 * @return the enum which is the next state
		 */
		@Override
		public ElevatorState nextState(boolean dir) {
			if(dir != false) {
				return ReadyUp;
			}
			else {
				return ReadyDown;
			}
			
		}
		
	},
	ReadyUp{ //The state of being ready to go up
		@Override
		/**
		 * returns moving the purpose of the boolean is to make it consistent with the first method
		 */
		public ElevatorState nextState(boolean dir) {
			return Moving;
		}
		
	}, 
	ReadyDown{ //The state of being ready to go down
		@Override
		/**
		 * returns moving the purpose of the boolean is to make it consistent with the first method
		 */
		public ElevatorState nextState(boolean dir) {
			return Moving;
		}
		
	}, Moving{ //The state of currently moving that moves back to idle
		@Override
		/**
		 * returns moving the purpose of the boolean is to make it consistent with the first method
		 */
		public ElevatorState nextState(boolean dir) {
			return Idle;
		}		
		
	};

	public abstract ElevatorState nextState(boolean dir);
}
