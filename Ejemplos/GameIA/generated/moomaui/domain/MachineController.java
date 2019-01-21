package moomaui.domain;

import java.util.LinkedList;

public class MachineController {
	private IMooreMachine machine;
	private MachineSimulator simulator;
	private GameAIEnvironment environment;
	
	public MachineController(IMooreMachine machine) {
		this.machine = machine;
		this.simulator = new MachineSimulator(machine);
		this.environment = new GameAIEnvironment();
	}
	
	public LinkedList<IState> getStates() {
		return machine.getStates();
	}
	
	public LinkedList<ITransition> getTransitions() {
		return machine.getTransitions();
	}
	
	public GameAIEnvironment getEnvironment() {
		return environment;
	}
	
	public IState getInitialState() {
		return machine.getInitialState();
	}
	
	public IState getTransitionDestination(String stateName, String input) {
		IState originState = new State(stateName);
		return machine.getDestinationState(originState, input);
	}
	
	public IState addNewInput(Object input) {		
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
