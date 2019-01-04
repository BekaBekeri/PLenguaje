state = """
public class State implements IState {{
	protected String name;
	private OutputInterface output;
	
	public State(String name) {{
		this.name = name;
	}}
	
	@Override
	public boolean equals(Object obj) {{
		if (obj instanceof State) {{
			return ((State) obj).name.equals(this.name);
		}}
		return false;
	}}
	
	public String getName() {{
		return name;
	}}
	public void setName(String name) {{
		this.name = name;
	}}

	public OutputInterface getOutput() {{
		return output;
	}}

	public void setOutput(OutputInterface output) {{
		this.output = output;
	}}
}}
"""

istate = """
public interface IState {{
	String getName();
	void setName(String name);
	OutputInterface getOutput();
	void setOutput(OutputInterface output);
}}
"""

imooremachine = """
import java.util.LinkedList;

public interface IMooreMachine {{
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
}}
"""

mooremachine = """
import java.util.LinkedList;

public class MooreMachine implements IMooreMachine {{
	protected LinkedList<IState> states;
	protected LinkedList<ITransition> transitions;
	protected String name;
	protected IState initialState;
	
	public MooreMachine() {{
		states = new LinkedList<IState>();
		transitions = new LinkedList<ITransition>();
	}}
	
	@Override
	public boolean addState(IState state) {{
		if (!hasState(state)) {{
			states.add(state);
			return true;
		}}
		return false;
		
	}}

	@Override
	public boolean addTransition(ITransition transition) {{
		if (hasState(transition.getFromState()) && hasState(transition.getToState())) {{
			transitions.add(transition);
			return true;
		}}
		return false;
	}}
	
	@Override
	public boolean removeState(IState state) {{
		return false;
	}}
	@Override
	public boolean removeTransition(ITransition transition) {{
		return false;
	}}

	@Override
	public LinkedList<IState> getStates() {{
		return this.states;
	}}

	@Override
	public IState getDestinationState(IState originState, String input) {{
		for (ITransition t : transitions) {{
			if (t.getFromState().equals(originState) && t.getInputs().contains(input))
				return t.getToState();
		}}
		return null;
	}}
	
	@Override
	public LinkedList<ITransition> getTransitions() {{
		return this.transitions;
	}}

	@Override
	public IState getInitialState() {{
		return this.initialState;
	}}

	@Override
	public void setInitialState(IState initialState) {{
		this.setInitialState(initialState, false);
	}}

	@Override
	public void setInitialState(IState initialState, boolean overwrite) {{
		if (initialState == null && !overwrite) {{
			throw new RuntimeException("The initial state is already defined");
		}} else {{
			this.initialState = initialState;
		}}
	}}
	
	@Override
	public String getMachineName() {{
		return name;
	}}
	
	@Override
	public void setMachineName(String name) {{
		this.name = name;
	}}
	
	public boolean hasState(IState state) {{
		for (IState st : states) {{
			if (state.equals(st))
				return true;
		}}
		return false;
	}}
}}
"""

itransition = """
import java.util.LinkedList;

public interface ITransition {{
	IState getFromState();
	void setFromState(IState fromState);
	IState getToState();
	void setToState(IState toState);
	LinkedList<String> getInputs();
	void setInputs(LinkedList<String> inputs);
}}
"""

transition = """
import java.util.Arrays;
import java.util.LinkedList;

public class Transition implements ITransition {{
	protected IState fromState;
	protected IState toState;
	protected LinkedList<String> inputs;
	
	public Transition(IState fromState, IState toState) {{
		this.setFromState(fromState);
		this.setToState(toState);
		this.inputs = new LinkedList<String>();
	}}
	
	public Transition(IState fromState, IState toState, String input) {{
		this.setFromState(fromState);
		this.setToState(toState);
		this.inputs = new LinkedList<String>();
		this.inputs.add(input);
	}}
	
	public Transition(IState fromState, IState toState, LinkedList<String> inputs) {{
		this.setFromState(fromState);
		this.setToState(toState);
		this.inputs = inputs;
	}}
	
	public Transition(IState fromState, IState toState, String[] inputs) {{
		this(fromState, toState, new LinkedList<String>(Arrays.asList(inputs)));
	}}

	public IState getFromState() {{
		return fromState;
	}}

	public void setFromState(IState fromState) {{
		this.fromState = fromState;
	}}

	public IState getToState() {{
		return toState;
	}}

	public void setToState(IState toState) {{
		this.toState = toState;
	}}

	public LinkedList<String> getInputs() {{
		return inputs;
	}}

	public void setInputs(LinkedList<String> inputs) {{
		this.inputs = inputs;
	}}
	
	@Override
	public boolean equals(Object obj) {{
		if (obj instanceof Transition) {{
			Transition t = (Transition) obj;
			return this.getFromState().equals(t.getFromState()) && 
					this.getToState().equals(t.getToState()) && 
					this.getInputs().containsAll(t.getInputs());
		}}
		return false;
	}}
}}
"""

machinecontroller = """
import java.util.LinkedList;

public class MachineController {{
	private IMooreMachine machine;
	private MachineSimulator simulator;
	private {0} environment;
	
	public MachineController(IMooreMachine machine) {{
		this.machine = machine;
		this.simulator = new MachineSimulator(machine);
		this.environment = new {0}();
	}}
	
	public LinkedList<IState> getStates() {{
		return machine.getStates();
	}}
	
	public LinkedList<ITransition> getTransitions() {{
		return machine.getTransitions();
	}}
	
	public {0} getEnvironment() {{
		return environment;
	}}
	
	public IState getInitialState() {{
		return machine.getInitialState();
	}}
	
	public IState getTransitionDestination(String stateName, String input) {{
		IState originState = new State(stateName);
		return machine.getDestinationState(originState, input);
	}}
	
	public IState addNewInput(Object input) {{		
		boolean transitioned = simulator.addNewInput(environment.translate(input));
		if (transitioned)
			return simulator.getCurrentState();
		else
			return null;
	}}
	
	public IState removeInput() {{
		return simulator.removeInput();
	}}
	
	public IState getCurrentState() {{
		return simulator.getCurrentState();
	}}
	
	public IState getPreviousState() {{
		return simulator.getPreviousState();
	}}
}}
"""

ienvironment = """
public interface IEnvironment {{
	String translate(Object input);
}}
"""

machinesimulator = """
import java.util.Stack;

public class MachineSimulator {{
	private IMooreMachine machine;
	private IState currentState;
	private Stack<IState> previousStates = new Stack<>();
	private Stack<String> previousInputs = new Stack<>();
		
	public MachineSimulator(IMooreMachine machine) {{
		this.setMachine(machine);
		this.currentState = this.machine.getInitialState();
	}}
	
	public boolean addNewInput(String input) {{
		IState destinationState = getMachine().getDestinationState(getCurrentState(), input);
		if (destinationState == null) {{
			return false;
		}}
		
		previousStates.push(currentState);
		previousInputs.push(input);
		setCurrentState(destinationState);
		//getCurrentState().getOutput().run();
		return true;
	}}
	
	public IState removeInput() {{
		if (!previousStates.isEmpty()) {{
			previousInputs.pop();
			IState previousState = previousStates.pop();
			this.currentState = previousState;
			return previousState;
		}}
		return null;
	}}

	public IMooreMachine getMachine() {{
		return machine;
	}}

	public void setMachine(IMooreMachine machine) {{
		this.machine = machine;
	}}

	public IState getCurrentState() {{
		return currentState;
	}}

	public void setCurrentState(IState currentState) {{
		this.currentState = currentState;
	}}
	
	public IState getPreviousState() {{
		if (previousStates.size() == 0) {{
			return null;
		}} else {{
			return previousStates.peek();
		}}
	}}
}}
"""

outputinterface = """
import java.util.function.Consumer;

@FunctionalInterface
public interface OutputInterface extends Consumer<{}> {{}}
"""