package moomaui.domain;

public class State implements IState {
	protected String name;
	private OutputInterface output;
	
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

	public OutputInterface getOutput() {
		return output;
	}

	public void setOutput(OutputInterface output) {
		this.output = output;
	}
}
