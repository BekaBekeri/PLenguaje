package moomaui.domain;

public class State {
	protected String name;
	private OutputInterface action;
	
	public State(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof State) {
			return ((State) obj).name.equals(this.name);
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public OutputInterface getAction() {
		return action;
	}

	public void setAction(OutputInterface action) {
		this.action = action;
	}
}
