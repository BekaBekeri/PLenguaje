package moomaui.domain;

public class State {
	protected int ID;
	protected String name;
	
	public State(int ID) {
		this.ID = ID;
		this.name = "";
	}
	
	public State(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof State) {
			return ((State) obj).ID == this.ID;
		}
		return false;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
