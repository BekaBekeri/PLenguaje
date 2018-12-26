package moomaui.domain;

import java.util.LinkedList;

public class MachineController<T> {
	private IMooreMachine machine;
	private MachineSimulator simulator;
	private GenericEnvironment<T> environment;
	
	public MachineController(IMooreMachine machine) {
		this.machine = machine;
		this.simulator = new MachineSimulator(machine);
		this.environment = new GenericEnvironment<T>();
	}
	
	public LinkedList<IState> getStates() {
		return machine.getStates();
	}
	
	public LinkedList<ITransition> getTransitions() {
		return machine.getTransitions();
	}
	
	public IState getInitialState() {
		return machine.getInitialState();
	}
	
	public IState getTransitionDestination(String stateName, String input) {
		IState originState = new State(stateName);
		return machine.getDestinationState(originState, input);
	}
	
	public IState addNewInput(T input) {		
		boolean transitioned = simulator.addNewInput(environment.translate(input));
		if (transitioned)
			return simulator.getCurrentState();
		else
			return null;
	}
	
	public IState removeInput() {
		return simulator.removeInput();
	}
	
	public IState getCurrentState() {
		return simulator.getCurrentState();
	}
	
	public IState getPreviousState() {
		return simulator.getPreviousState();
	}
}
