package moomaui.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameAIEnvironment implements IEnvironment {
	private HashMap<String, List<Runnable>> listeners;
	
	public GameAIEnvironment() {
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
		if (input.toString().equalsIgnoreCase("mirando")) {
			return "1";
		} else if (input.toString().equalsIgnoreCase("no mirando")) {
			return "2";
		} else if (input.toString().equalsIgnoreCase("no lejos")) {
			return "5";
		} else if (input.toString().equalsIgnoreCase("cerca atacar")) {
			return "10";
		} else {
			return input.toString();
		}
	}

}
