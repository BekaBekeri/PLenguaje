package moomaui.domain;

import java.util.LinkedList;

public interface ITransition {
	IState getFromState();
	void setFromState(IState fromState);
	IState getToState();
	void setToState(IState toState);
	LinkedList<String> getInputs();
	void setInputs(LinkedList<String> inputs);
}
