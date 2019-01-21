package moomaui.domain;

import java.util.LinkedList;

public class MooreMachine implements IMooreMachine {
	protected LinkedList<IState> states;
	protected LinkedList<ITransition> transitions;
	protected String name;
	protected IState initialState;
	
	public MooreMachine() {
		states = new LinkedList<IState>();
		transitions = new LinkedList<ITransition>();
	}
	
	@Override
	public boolean addState(IState state) {
		if (!hasState(state)) {
			states.add(state);
			return true;
		}
		return false;
		
	}

	@Override
	public boolean addTransition(ITransition transition) {
		if (hasState(transition.getFromState()) && hasState(transition.getToState())) {
			transitions.add(transition);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeState(IState state) {
		return false;
	}
	@Override
	public boolean removeTransition(ITransition transition) {
		return false;
	}

	@Override
	public LinkedList<IState> getStates() {
		return this.states;
	}

	@Override
	public IState getDestinationState(IState originState, String input) {
		for (ITransition t : transitions) {
			if (t.getFromState().equals(originState) && t.getInputs().contains(input))
				return t.getToState();
		}
		return null;
	}
	
	@Override
	public LinkedList<ITransition> getTransitions() {
		return this.transitions;
	}

	@Override
	public IState getInitialState() {
		return this.initialState;
	}

	@Override
	public void setInitialState(IState initialState) {
		this.setInitialState(initialState, false);
	}

	@Override
	public void setInitialState(IState initialState, boolean overwrite) {
		if (initialState == null && !overwrite) {
			throw new RuntimeException("The initial state is already defined");
		} else {
			this.initialState = initialState;
		}
	}
	
	@Override
	public String getMachineName() {
		return name;
	}
	
	@Override
	public void setMachineName(String name) {
		this.name = name;
	}
	
	public boolean hasState(IState state) {
		for (IState st : states) {
			if (state.equals(st))
				return true;
		}
		return false;
	}
}
