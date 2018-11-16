package moomaui.domain;

import java.util.Stack;

public class MachineSimulator {
	private IMooreMachine machine;
	private IState currentState;
	private Stack<IState> previousStates = new Stack<>();
	private Stack<String> previousInputs = new Stack<>();
		
	public MachineSimulator(IMooreMachine machine) {
		this.setMachine(machine);
		this.currentState = this.machine.getInitialState();
	}
	
	public boolean addNewInput(String input) {
		IState destinationState = getMachine().getDestinationState(getCurrentState(), input);
		if (destinationState == null) {
			return false;
		}
		
		previousStates.push(currentState);
		previousInputs.push(input);
		setCurrentState(destinationState);
		//getCurrentState().getOutput().run();
		return true;
	}

	public IMooreMachine getMachine() {
		return machine;
	}

	public void setMachine(IMooreMachine machine) {
		this.machine = machine;
	}

	public IState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(IState currentState) {
		this.currentState = currentState;
	}
	
	public IState getPreviousState() {
		if (previousStates.size() == 0) {
			return null;
		} else {
			return previousStates.peek();
		}
	}
}
