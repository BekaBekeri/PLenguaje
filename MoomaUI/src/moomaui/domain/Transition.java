package moomaui.domain;

public class Transition<T extends State> {
	protected T fromState;
	protected T toState;
	protected String input;
	
	public Transition(T fromState, T toState) {
		this.setFromState(fromState);
		this.setToState(toState);
		this.setInput("");
	}
	
	public Transition(T fromState, T toState, String input) {
		this.setFromState(fromState);
		this.setToState(toState);
		this.setInput(input);
	}

	public T getFromState() {
		return fromState;
	}

	public void setFromState(T fromState) {
		this.fromState = fromState;
	}

	public T getToState() {
		return toState;
	}

	public void setToState(T toState) {
		this.toState = toState;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transition) {
			@SuppressWarnings("unchecked")
			Transition<T> t = (Transition<T>) obj;
			return this.getFromState().equals(t.getFromState()) && this.getToState().equals(t.getToState()) && this.getInput().equals(t.getInput());
		}
		return false;
	}
}
