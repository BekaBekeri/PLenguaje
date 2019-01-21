package moomaui.domain;

public interface IState {
	String getName();
	void setName(String name);
	OutputInterface getOutput();
	void setOutput(OutputInterface output);
}
