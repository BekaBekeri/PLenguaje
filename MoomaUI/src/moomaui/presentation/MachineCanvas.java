package moomaui.presentation;

import java.util.LinkedList;

import moomaui.domain.DrawableState;
import moomaui.domain.DrawableTransition;
import moomaui.domain.IMooreMachine;
import moomaui.domain.MooreMachine;
import moomaui.drawing.JDrawer;

public class MachineCanvas extends JDrawer implements IMooreMachine<DrawableState, DrawableTransition> {
	private static final long serialVersionUID = 1L;
	private IMooreMachine<DrawableState, DrawableTransition> mMachine;
	private DrawableState selectedState = null;
	
	public static final int STATE_RADIUS = 28;
	public static final int STATE_OFFSET = 6;
	public static final int TRANSITION_STROKE = 2;
	public static final int ARROW_SIZE = 7;

	public MachineCanvas() {
		mMachine = new MooreMachine<DrawableState, DrawableTransition>();
	}
	
	public MachineCanvas(IMooreMachine<DrawableState, DrawableTransition> mMachine) {
		this.mMachine = mMachine;
		
		for (DrawableState state : mMachine.getStates()) {
			this.addGraphicObject(state);
		}
		for (DrawableTransition transition : mMachine.getTransitions()) {
			this.addGraphicObject(transition);
		}
	}
	
	@Override
	public boolean addState(DrawableState state) {
		if (mMachine.addState(state)) {
			this.addGraphicObject(state);
			return true;
		}
		return false;
	}
	@Override
	public boolean addTransition(DrawableTransition transition) {
		if (mMachine.addTransition(transition)) {
			this.addGraphicObject(transition);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeState(DrawableState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTransition(DrawableTransition transition) {
		// TODO Auto-generated method stub
		return false;
	}

	public DrawableState getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(DrawableState selectedState) {
		this.selectedState = selectedState;
	}

	@Override
	public LinkedList<DrawableState> getStates() {
		return mMachine.getStates();
	}

	@Override
	public LinkedList<DrawableTransition> getTransitions() {
		return mMachine.getTransitions();
	}

	@Override
	public String getMachineName() {
		return mMachine.getMachineName();
	}

	@Override
	public void setMachineName(String name) {
		mMachine.setMachineName(name);
	}

	@Override
	public int getNextID() {
		return mMachine.getNextID();
	}
	
	public DrawableState getStateInPosition(int x, int y) {
		DrawableState state = null;
		double best = 1000000000;
		
		for (DrawableState st : this.getStates()) {
			double dist = euclideanDistance(x, st.getX(), y, st.getY());
			if (dist < st.getRadius() && dist < best) {
				best = dist;
				state = st;
			}
		}
		return state;
	}
	
	public static boolean isPointInCircle(int x, int y, int c1, int c2, int radius) {
		return euclideanDistance(x, c1, y, c2) < radius;
	}
	
	public static double euclideanDistance(int x1, int x2, int y1, int y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	
	public static double angleBetweenPoints(int x1, int x2, int y1, int y2) {
		return Math.atan2(y2 - y1, x2 - x1);
	}
}
