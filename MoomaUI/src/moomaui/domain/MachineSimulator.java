package moomaui.domain;

import java.util.LinkedList;

public class MachineSimulator<S extends State, T extends Transition<S>> {
	private IMooreMachine<S,T> machine;
	private S currentState;
		
	public MachineSimulator(IMooreMachine<S, T> machine) {
		this.setMachine(machine);
	}
	
	public boolean addNewInput(String input) {
		LinkedList<S> destinationStates = getMachine().getDestinationStates(getCurrentState(), input);
		if (destinationStates.size() != 1) {
			return false;
		}
		
		setCurrentState(destinationStates.get(0));
		getCurrentState().getAction().output();
		return true;
	}

	public IMooreMachine<S,T> getMachine() {
		return machine;
	}

	public void setMachine(IMooreMachine<S,T> machine) {
		this.machine = machine;
	}

	public S getCurrentState() {
		return currentState;
	}

	public void setCurrentState(S currentState) {
		this.currentState = currentState;
	}
}
