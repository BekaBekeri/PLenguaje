package moomaui.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class VendingEnvironment implements IEnvironment {
	private HashMap<String, List<Runnable>> listeners;
	
	public VendingEnvironment() {
		listeners = new HashMap<>();
	}
	
	public void notify(String state) {
		if (listeners.containsKey(state)) {
			listeners.get(state).forEach((Runnable action) -> action.run());
		}
	}
	
	public void addListener(String state, Runnable action) {
		if (!listeners.containsKey(state))
			listeners.put(state, new LinkedList<>());
		
		listeners.get(state).add(action);
	}
	
	@Override
	public String translate(Object input) {
		if (input.toString().equalsIgnoreCase("Bebida")) {
			return "20";
		} else if (input.toString().equalsIgnoreCase("Bolsa")) {
			return "30";
		} else {
			return input.toString();
		}
	}

}
