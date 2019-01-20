package moomaui.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import moomaui.domain.IState;
import moomaui.domain.ITransition;
import moomaui.domain.MachineController;
import moomaui.presentation.drawing.DrawableState;
import moomaui.presentation.drawing.DrawableTransition;
import moomaui.presentation.drawing.JDrawer;

public class MachineCanvas extends JDrawer{
	private static final long serialVersionUID = 1L;
	private MachineController controller;
	private DrawableState selectedState = null;
	private boolean isInitialized = false; // Indicates whether the state positions are initialized

	private LinkedList<DrawableState> states = new LinkedList<>();
	private LinkedList<DrawableTransition> transitions = new LinkedList<>();
	private String machineName;
	private DrawableState currentState;
	
	public static final int STATE_RADIUS = 28;
	public static final int STATE_OFFSET = 6;
	public static final int TRANSITION_STROKE = 2;
	public static final int FONT_SIZE = 15;
	public static final int ARROW_SIZE = 7;
	public static final int INITIAL_ARROW_SIZE = 11;
	public static final int MACHINE_DEFAULT_RADIUS = 160;
	public static final int ARC_LINE_CONTROL_POINT_OFFSET = 75;
	public static final int ARC_LINE_CONTROL_POINT_TEXT_OFFSET = ARC_LINE_CONTROL_POINT_OFFSET - 13;
	public static final String TRANSITION_SEPARATOR = ", ";
	public static final int TEXT_FROM_LINE_SEPARATION = 15;
	public static final int SELF_TRANSITION_CIRCLE_CENTER_OFFSET = 21 * STATE_RADIUS / 20;
	public static final int SELF_TRANSITION_CIRCLE_RADIUS = 25;

	public static final Color OUTSIDE_CIRCLE_STATE_DEFAULT = Color.BLACK;
	public static final Color INSIDE_CIRCLE_STATE_DEFAULT = Color.LIGHT_GRAY;
	public static final Color INSIDE_CIRCLE_STATE_SELECTED = Color.GREEN;
	public static final Color INSIDE_CIRCLE_STATE_CANDIDATE = Color.CYAN;
	
	public MachineCanvas(MachineController controller) {
		this.controller = controller;
		
		for (IState state : controller.getStates()) {
			DrawableState newState = new DrawableState(state.getName());
			states.add(newState);
			this.addGraphicObject(newState);
			
			if (state.equals(controller.getInitialState())) {
				newState.setInitial(true);
			}
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
					continue;
				} 
				if (t1.getFromState().equals(t2.getToState()) && t1.getToState().equals(t2.getFromState())) {
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
	
	public MachineController getController() {
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
	
	public static int[][] getCircleIntersectionPoints(int x1, int y1, int r1, int x2, int y2, int r2) {
		double distanceBetweenCenters = euclideanDistance(x1, x2, y1, y2);
		
		// http://www.ambrsoft.com/TrigoCalc/Circles2/circle2intersection/CircleCircleIntersection.htm
		// If the sum of the radii is greater than the distance between them 
		// then we have intersection
		if (r1 + r2 > distanceBetweenCenters) {
			// Area of the triangle formed by the two centers and one intersection point
			double areaTriangle = distanceBetweenCenters + r1 + r2;
			areaTriangle *= distanceBetweenCenters + r1 - r2;
			areaTriangle *= distanceBetweenCenters - r1 + r2;
			areaTriangle *= - distanceBetweenCenters + r1 + r2;
			
			areaTriangle = Math.sqrt(areaTriangle);
			areaTriangle /= 4; // Using Heron's formula
			
			
			double intersectionPointX = (double) (x1 + x2) / 2.0;
			intersectionPointX += ((x2 - x1) * (Math.pow(r1, 2) - Math.pow(r2, 2))) 
					/ (2 * Math.pow(distanceBetweenCenters, 2));

			double intersectionPointX1 = intersectionPointX + 
					2 * ((y1 - y2) / Math.pow(distanceBetweenCenters, 2)) * areaTriangle;
			double intersectionPointX2 = intersectionPointX - 
					2 * ((y1 - y2) / Math.pow(distanceBetweenCenters, 2)) * areaTriangle;
			
			double intersectionPointY = (double) (y1 + y2) / 2.0;
			intersectionPointY += ((y2 - y1) * (Math.pow(r1, 2) - Math.pow(r2, 2))) 
					/ (2 * Math.pow(distanceBetweenCenters, 2));

			double intersectionPointY1 = intersectionPointY - 
					2 * ((x1 - x2) / Math.pow(distanceBetweenCenters, 2)) * areaTriangle;
			double intersectionPointY2 = intersectionPointY + 
					2 * ((x1 - x2) / Math.pow(distanceBetweenCenters, 2)) * areaTriangle;
			
			int[][] points = new int[2][2];
			points[0][0] = (int) intersectionPointX1;
			points[0][1] = (int) intersectionPointY1;
			points[1][0] = (int) intersectionPointX2;
			points[1][1] = (int) intersectionPointY2;
			
			return points;
		} else {
			return null;
		}
	}
}
