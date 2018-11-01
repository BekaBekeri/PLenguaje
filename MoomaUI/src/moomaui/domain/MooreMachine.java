package moomaui.domain;

import java.util.LinkedList;

public class MooreMachine<S extends State,T extends Transition<S>> implements IMooreMachine<S, T> {
	protected LinkedList<S> states;
	protected LinkedList<T> transitions;
	protected String name = "Machine";
	private int nextID = 1;
	
	public MooreMachine() {
		states = new LinkedList<S>();
		transitions = new LinkedList<T>();
	}
	
	@Override
	public boolean addState(S state) {
		if (!hasState(state)) {
			states.add(state);
			return true;
		}
		return false;
		
	}

	@Override
	public boolean addTransition(T transition) {
		if (hasState(transition.getFromState()) && hasState(transition.getToState())) {
			transitions.add(transition);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeState(S state) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeTransition(T transition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<S> getStates() {
		return this.states;
	}
	
	@Override
	public LinkedList<T> getTransitions() {
		return this.transitions;
	}
	
	@Override
	public int getNextID() {
		return nextID++;
	}
	
	@Override
	public String getMachineName() {
		return name;
	}
	
	@Override
	public void setMachineName(String name) {
		this.name = name;
	}
	
	public boolean hasState(State state) {
		for (State st : states) {
			if (state.equals(st))
				return true;
		}
		return false;
	}

}
