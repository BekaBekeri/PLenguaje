package moomaui.domain;

import java.util.HashMap;
import java.util.Stack;
import java.util.function.Consumer;

public class MachineSimulator {
	private IMooreMachine machine;
	private IState currentState;
	private Stack<IState> previousStates = new Stack<>();
	private Stack<String> previousInputs = new Stack<>();
	private HashMap<String, Consumer<String>> observers;
		
	public MachineSimulator(IMooreMachine machine) {
		this.setMachine(machine);
		this.currentState = this.machine.getInitialState();
		observers = new HashMap<>();
	}
	
	public void setObservers(HashMap<String, Consumer<String>> observers) {
		this.observers = observers;
	}
	
	public boolean addNewInput(String input) {
		IState destinationState = getMachine().getDestinationState(getCurrentState(), input);
		if (destinationState == null) {
			return false;
		}

		if (observers != null && observers.containsKey(input)) {
			observers.get(input).accept(currentState.getName());
		}
		
		previousStates.push(currentState);
		previousInputs.push(input);
		setCurrentState(destinationState);
		
		return true;
	}
	
	public IState removeInput() {
		if (!previousStates.isEmpty()) {
			previousInputs.pop();
			IState previousState = previousStates.pop();
			this.currentState = previousState;
			return previousState;
		}
		return null;
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
