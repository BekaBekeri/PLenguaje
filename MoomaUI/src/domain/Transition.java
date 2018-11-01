package domain;

public class Transition<T extends State> {
	protected T fromState;
	protected T toState;
	protected String input;
	
	public Transition(T fromState, T toState) {
		this.setFromState(fromState);
		this.setToState(toState);
		this.input = "";
	}
	
	public Transition(T fromState, T toState, String input) {
		this.setFromState(fromState);
		this.setToState(toState);
		this.input = input;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transition) {
			@SuppressWarnings("unchecked")
			Transition<T> t = (Transition<T>) obj;
			return this.getFromState().equals(t.getFromState()) && this.getToState().equals(t.getToState()) && this.input.equals(t.input);
		}
		return false;
	}
}
