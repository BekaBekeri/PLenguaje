package moomaui.domain;

import java.util.LinkedList;

public interface IMooreMachine {
	boolean addState(IState state);
	boolean addTransition(ITransition transition);
	boolean removeState(IState state);
	boolean removeTransition(ITransition transition);
	
	LinkedList<IState> getStates();
	IState getDestinationState(IState fromState, String input);
	LinkedList<ITransition> getTransitions();
	IState getInitialState();
	void setInitialState(IState initialState);
	void setInitialState(IState initialState, boolean overwrite);
	
	String getMachineName();
	void setMachineName(String name);
}
