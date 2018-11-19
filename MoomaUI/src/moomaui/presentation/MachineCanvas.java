package moomaui.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import moomaui.domain.IState;
import moomaui.domain.ITransition;
import moomaui.domain.MachineController;
import moomaui.domain.OutputInterface;
import moomaui.presentation.drawing.DrawableState;
import moomaui.presentation.drawing.DrawableTransition;
import moomaui.presentation.drawing.JDrawer;

public class MachineCanvas extends JDrawer implements IState{
	private static final long serialVersionUID = 1L;
	private MachineController<String> controller;
	private DrawableState selectedState = null;
	private boolean isInitialized = false; // Indicates whether the state positions are initialized

	private LinkedList<DrawableState> states = new LinkedList<>();
	private LinkedList<DrawableTransition> transitions = new LinkedList<>();
	private String machineName;
	private DrawableState initialState;
	private DrawableState currentState;
	
	public static final int STATE_RADIUS = 28;
	public static final int STATE_OFFSET = 6;
	public static final int TRANSITION_STROKE = 2;
	public static final int FONT_SIZE = 15;
	public static final int ARROW_SIZE = 7;
	public static final int INITIAL_ARROW_SIZE = 11;
	public static final int MACHINE_DEFAULT_RADIUS = 130;
	public static final int ARC_LINE_CONTROL_POINT_OFFSET = 75;
	public static final int ARC_LINE_CONTROL_POINT_TEXT_OFFSET = 62;
	public static final String TRANSITION_SEPARATOR = ", ";
	public static final int TEXT_FROM_LINE_SEPARATION = 15;

	public static final Color OUTSIDE_CIRCLE_STATE_DEFAULT = Color.BLACK;
	public static final Color INSIDE_CIRCLE_STATE_DEFAULT = Color.LIGHT_GRAY;
	public static final Color INSIDE_CIRCLE_STATE_SELECTED = Color.GREEN;
	public static final Color INSIDE_CIRCLE_STATE_CANDIDATE = Color.CYAN;
	
	public MachineCanvas(MachineController<String> controller) {
		this.controller = controller;
		
		for (IState state : controller.getStates()) {
			DrawableState newState = new DrawableState(state.getName());
			states.add(newState);
			this.addGraphicObject(newState);
			
			if (state.equals(controller.getInitialState())) 
				this.initialState = newState;
		}
		for (ITransition transition : controller.getTransitions()) {
			DrawableState fromState = states.get(states.indexOf(new DrawableState(transition.getFromState().getName())));
			DrawableState toState = states.get(states.indexOf(new DrawableState(transition.getToState().getName())));
			DrawableTransition newTransition = new DrawableTransition(fromState, toState, transition.getInputs());
			transitions.add(newTransition);
			this.addGraphicObject(newTransition);
		}
	}
	
	@Override
	public void paint(Graphics g){
		if (!isInitialized) {
			initializePositions();
			initializeStates();
			initializeTransitions();
			controller.getCurrentState().getOutput().run();
			isInitialized = true;
		}
		
		super.paint(g);
	}

	private void initializePositions() {
		int[][] coords = MachineCanvas.generateRegularPolygon(states.size(), MACHINE_DEFAULT_RADIUS);
		Dimension size = this.getSize();
		int xOffset = size.width / 2;
		int yOffset = size.height / 2;
		for (int i = 0; i < states.size(); i++) {
			DrawableState st = states.get(i);
			st.setX(coords[i][0] + xOffset);
			st.setY(coords[i][1] + yOffset);
		}
	}
	
	private void initializeStates() {
		for (DrawableState state : states) {
			if (state.equals(new DrawableState(controller.getCurrentState().getName()))) {
				state.setInnerColor(MachineCanvas.INSIDE_CIRCLE_STATE_SELECTED);
				this.currentState = state;
			}
		}
	}
	
	private void initializeTransitions() {
		for (DrawableTransition t1 : transitions) {
			for (DrawableTransition t2 : transitions) {
				if (t1.getFromState().equals(t1.getToState())) {
					/*if (t1.getFromState().equals(initialState))
						t1.setInitial(true);*/						
				} 
				else if (t1.getFromState().equals(t2.getToState()) && t1.getToState().equals(t2.getFromState())) {
					t1.setIsCurved(true);
					t2.setIsCurved(true);
				}
			}
		}
	}
	
	public DrawableState getCurrentState() {
		return this.currentState;
	}
	
	public void setCurrentState(DrawableState currentState) {
		this.currentState = currentState;
	}
	
	public MachineController<String> getController() {
		return this.controller;
	}

	public DrawableState getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(DrawableState selectedState) {
		this.selectedState = selectedState;
	}

	public LinkedList<DrawableState> getStates() {
		return states;
	}

	public IState getDestinationStates(DrawableState originState, String input) {
		return controller.getTransitionDestination(originState.getText(), input);
	}

	public LinkedList<DrawableTransition> getTransitions() {
		return transitions;
	}

	public String getMachineName() {
		return this.machineName;
	}

	public void setMachineName(String name) {
		this.machineName = name;
	}
	
	public DrawableState getStateInPosition(int x, int y) {
		DrawableState state = null;
		double best = Double.MAX_VALUE;
		
		for (DrawableState st : this.getStates()) {
			double dist = euclideanDistance(x, st.getX(), y, st.getY());
			if (dist < st.getRadius() && dist < best) {
				best = dist;
				state = st;
			}
		}
		return state;
	}

	@Override
	public OutputInterface getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOutput(OutputInterface output) {
		// TODO Auto-generated method stub
		
	}
}
