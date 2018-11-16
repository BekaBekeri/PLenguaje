package moomaui.domain;

import java.util.Arrays;
import java.util.LinkedList;

public class Transition implements ITransition {
	protected IState fromState;
	protected IState toState;
	protected LinkedList<String> inputs;
	
	public Transition(IState fromState, IState toState) {
		this.setFromState(fromState);
		this.setToState(toState);
		this.inputs = new LinkedList<String>();
	}
	
	public Transition(IState fromState, IState toState, String input) {
		this.setFromState(fromState);
		this.setToState(toState);
		this.inputs = new LinkedList<String>();
		this.inputs.add(input);
	}
	
	public Transition(IState fromState, IState toState, LinkedList<String> inputs) {
		this.setFromState(fromState);
		this.setToState(toState);
		this.inputs = inputs;
	}
	
	public Transition(IState fromState, IState toState, String[] inputs) {
		this(fromState, toState, new LinkedList<String>(Arrays.asList(inputs)));
	}

	public IState getFromState() {
		return fromState;
	}

	public void setFromState(IState fromState) {
		this.fromState = fromState;
	}

	public IState getToState() {
		return toState;
	}

	public void setToState(IState toState) {
		this.toState = toState;
	}

	public LinkedList<String> getInputs() {
		return inputs;
	}

	public void setInputs(LinkedList<String> inputs) {
		this.inputs = inputs;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transition) {
			Transition t = (Transition) obj;
			return this.getFromState().equals(t.getFromState()) && 
					this.getToState().equals(t.getToState()) && 
					this.getInputs().containsAll(t.getInputs());
		}
		return false;
	}
}
