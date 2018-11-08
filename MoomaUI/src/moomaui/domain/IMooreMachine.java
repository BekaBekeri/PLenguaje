package moomaui.domain;

import java.util.LinkedList;

public interface IMooreMachine<S, T> {
	boolean addState(S state);
	boolean addTransition(T transition);
	boolean removeState(S state);
	boolean removeTransition(T transition);
	LinkedList<S> getStates();
	LinkedList<S> getDestinationStates(S originState, String input);
	LinkedList<T> getTransitions();
	String getMachineName();
	void setMachineName(String name);
}
