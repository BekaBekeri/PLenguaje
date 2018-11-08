package moomaui.presentation.drawing;

import java.awt.Color;

import moomaui.domain.Transition;
import moomaui.presentation.MachineCanvas;

public class DrawableTransition extends Transition<DrawableState> implements ArcArrowLineObject, TextObject {
	protected Color color = Color.BLACK;
	protected int stroke = MachineCanvas.TRANSITION_STROKE;
	protected int arrowSize = MachineCanvas.ARROW_SIZE;
	

	public DrawableTransition(DrawableState fromState, DrawableState toState) {
		super(fromState, toState);
	}
	
	public DrawableTransition(DrawableState fromState, DrawableState toState, String input) {
		super(fromState, toState, input);
	}
	
	@Override
	public int getX() {
		return this.getFromState().getX();
	}

	@Override
	public int getY() {
		return this.getFromState().getY();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public int getX1() {
		return this.getToState().getX();
	}

	@Override
	public int getY1() {
		return this.getToState().getY();
	}

	@Override
	public void setX(int X) {
		this.fromState.setX(X);
	}

	@Override
	public void setY(int Y) {
		this.fromState.setY(Y);
	}
	
	@Override
	public void setX1(int X1) {
		this.toState.setX(X1);
	}

	@Override
	public void setY1(int Y1) {
		this.toState.setY(Y1);
	}

	@Override
	public int getStroke() {
		return this.stroke;
	}

	@Override
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	@Override
	public int getArrowSize() {
		return this.arrowSize;
	}

	@Override
	public double getOrientation() {
		return MachineCanvas.angleBetweenPoints(fromState.getX(), toState.getX(), fromState.getY(), toState.getY());
	}

	@Override
	public void setArrowSize(int size) {
		this.arrowSize = size;
	}

	@Override
	public int getDeltaX1() {
		double radiusScale = Math.cos(MachineCanvas.angleBetweenPoints(fromState.getX(), toState.getX(), fromState.getY(), toState.getY()));
		return (int) (toState.getX() - (toState.getRadius() + arrowSize / 2) * radiusScale);
	}

	@Override
	public int getDeltaY1() {
		double radiusScale = Math.sin(MachineCanvas.angleBetweenPoints(fromState.getX(), toState.getX(), fromState.getY(), toState.getY()));
		return (int) (toState.getY() - (toState.getRadius() + arrowSize / 2) * radiusScale);
	}

	@Override
	public String getText() {
		return this.getInput();
	}

	@Override
	public void setText(String text) {
		this.setInput(text);
	}

}
