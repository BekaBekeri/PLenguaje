package moomaui.presentation.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;
import java.util.LinkedList;

import moomaui.presentation.MachineCanvas;

public class DrawableTransition implements ArcArrowLineObject, TextObject {
	protected DrawableState fromState;
	protected DrawableState toState;
	protected LinkedList<String> inputs;
	
	protected boolean isCurved;	
	protected boolean isSelf;
	protected Color color = Color.BLACK;
	protected int stroke = MachineCanvas.TRANSITION_STROKE;
	protected int arrowSize = MachineCanvas.ARROW_SIZE;
	private int fontSize = MachineCanvas.FONT_SIZE;
	private String transitionDisplaySeparator = MachineCanvas.TRANSITION_SEPARATOR;
	private int textFromLineSeparator = MachineCanvas.TEXT_FROM_LINE_SEPARATION;

	public DrawableTransition(DrawableState fromState, DrawableState toState, String input) {
		this.fromState = fromState;
		this.toState = toState;
		this.inputs = new LinkedList<String>();
		this.inputs.add(input);
		this.isSelf = fromState.equals(toState);
	}
	public DrawableTransition(DrawableState fromState, DrawableState toState, LinkedList<String> inputs) {
		this.fromState = fromState;
		this.toState = toState;
		this.inputs = new LinkedList<String>();
		this.isSelf = fromState.equals(toState);
		
		for (String input : inputs) {
			this.inputs.add(input);
		}
	}

	public DrawableState getFromState() {
		return fromState;
	}
	
	public void setFromState(DrawableState fromState) {
		this.fromState = fromState;
	}
	
	public DrawableState getToState() {
		return toState;
	}
	
	public void setToState(DrawableState toState) {
		this.toState = toState;
	}
	
	@Override
	public int getDeltaX1() {
		if (!isCurved) {
			double radiusScale = Math.cos(MachineCanvas.angleBetweenPoints(fromState.getX(), toState.getX(), fromState.getY(), toState.getY()));
			return (int) (toState.getX() - (toState.getRadius() + arrowSize / 2) * radiusScale);
		} else {
			int[] controlPoint = getControlPoint();
			
			double radiusScale = Math.cos(MachineCanvas.angleBetweenPoints(controlPoint[0], toState.getX(), controlPoint[1], toState.getY()));
			return (int) (toState.getX() - (toState.getRadius() + arrowSize / 2) * radiusScale);
		}
	}

	@Override
	public int getDeltaY1() {
		if (!isCurved) {
			double radiusScale = Math.sin(MachineCanvas.angleBetweenPoints(fromState.getX(), toState.getX(), fromState.getY(), toState.getY()));
			return (int) (toState.getY() - (toState.getRadius() + arrowSize / 2) * radiusScale);
		} else {
			int[] controlPoint = getControlPoint();
			
			double radiusScale = Math.sin(MachineCanvas.angleBetweenPoints(controlPoint[0], toState.getX(), controlPoint[1], toState.getY()));
			return (int) (toState.getY() - (toState.getRadius() + arrowSize / 2) * radiusScale);
		}
	}
	
	@Override
	public boolean isCurved() {
		return this.isCurved;
	}

	@Override
	public void setIsCurved(boolean isCurved) {
		this.isCurved = isCurved;
	}

	@Override
	public int getX1() {
		return this.toState.getX();
	}

	@Override
	public int getY1() {
		return this.toState.getY();
	}

	@Override
	public int getStroke() {
		return this.stroke;
	}

	@Override
	public void setX1(int X1) {
		// Use the state instead
	}

	@Override
	public void setY1(int Y1) {
		// Use the state instead
	}

	@Override
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	@Override
	public int getX() {
		return this.fromState.getX();
	}

	@Override
	public int getY() {
		return this.fromState.getY();
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setX(int X) {
		// Use the state instead
	}

	@Override
	public void setY(int Y) {
		// Use the state instead
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public int getArrowSize() {
		return this.arrowSize;
	}

	@Override
	public double getOrientation() {
		if (!isCurved) {
			return MachineCanvas.angleBetweenPoints(fromState.getX(), toState.getX(), fromState.getY(), toState.getY());
		} else {
			int[] controlPoint = getControlPoint();
			return MachineCanvas.angleBetweenPoints(controlPoint[0], getDeltaX1(), controlPoint[1], getDeltaY1());
		}
	}

	@Override
	public void setArrowSize(int size) {
		this.arrowSize = size;
	}

	@Override
	public String getText() {
		String text = "";
		for (String input : this.inputs) {
			text += transitionDisplaySeparator + input;
		}
		return text.substring(transitionDisplaySeparator.length());
	}

	@Override
	public void setText(String text) {
		throw new UnsupportedOperationException();		
	}
	
	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof DrawableTransition))
			return false;
		
		DrawableTransition t = (DrawableTransition) obj;
		return t.fromState.equals(this.fromState) && t.toState.equals(this.toState) && Arrays.deepEquals(t.inputs.toArray(), this.inputs.toArray());
	}

	@Override
	public void paint(Graphics2D g2) {
		if (!isCurved && !isSelf) {
			paintStraightArrow(g2);			
			paintStraightArrowText(g2);
		} else if (!isCurved && isSelf) {
			
		} else if (isCurved && !isSelf) {
			paintCurvedLineArrow(g2);
		} else {
			throw new InternalError("Transition cannot be curved and self");
		}
	}
	
	private Polygon getArrowInDeltaPositions() {
		return getArrowInDeltaPositions(this.getOrientation());
	}
	
	private Polygon getArrowInDeltaPositions(double orientation) {
		int[][] coords = MachineCanvas.generateRegularPolygon(3, this.getArrowSize(), this.getOrientation());
		Polygon arrow = new Polygon();
		for (int[] point : coords) {
			arrow.addPoint(point[0] + this.getDeltaX1(), point[1] + this.getDeltaY1());
		}
		return arrow;
	}

	private void paintStraightArrow(Graphics2D g2) {
		g2.setStroke(new BasicStroke(this.stroke));
		g2.setColor(this.color);
		g2.drawLine(this.fromState.getX(), this.fromState.getY(), this.getDeltaX1(), this.getDeltaY1());

		Polygon arrow = getArrowInDeltaPositions();
		g2.fillPolygon(arrow);
	}

	private void paintStraightArrowText(Graphics2D g2) {
		g2.setFont(new Font("Arial", Font.BOLD, fontSize));
		g2.setColor(this.color);
		int width = g2.getFontMetrics().stringWidth(getText());
		int height = g2.getFontMetrics().getHeight();
		
		double orientation = this.getOrientation();
		double perpendicular = orientation - Math.PI/2;

		int xOffset = (int) (Math.cos(perpendicular) * textFromLineSeparator);
		int yOffset = (int) (Math.sin(perpendicular) * textFromLineSeparator);
		int[] midPoint = MachineCanvas.midPoint(this.getX(), this.getX1(), this.getY(), this.getY1());
		
		g2.drawString(getText(), midPoint[0] + xOffset - width / 2, midPoint[1] + yOffset + height / 4);
	}
	
	
	private void paintCurvedLineArrow(Graphics2D g2) {
		paintCurvedLine(g2);
		paintCurvedArrow(g2);
		paintCurvedLineText(g2);
	}
	
	private int[] getControlPoint() {
		return getControlPoint(MachineCanvas.ARC_LINE_CONTROL_POINT_OFFSET);
	}
	
	private int[] getControlPoint(int offset) {
		int[] midPoint = MachineCanvas.midPoint(this.getX(), this.getX1(), this.getY(), this.getY1());
		double angle = MachineCanvas.angleBetweenPoints(this.getX(), midPoint[0], this.getY(), midPoint[1]);
		
		int xControlPointOffset = (int) (Math.cos(Math.toRadians(Math.toDegrees(angle) + 90)) * offset);
		int yControlPointOffset = (int) (Math.sin(Math.toRadians(Math.toDegrees(angle) + 90)) * offset);
		
		int xControlPoint = midPoint[0] - xControlPointOffset;
		int yControlPoint = midPoint[1] - yControlPointOffset;
		
		return new int[] {xControlPoint, yControlPoint};
	}
	
	private void paintCurvedLine(Graphics2D g2) {
		g2.setStroke(new BasicStroke(this.stroke));
		g2.setColor(this.color);

		GeneralPath path = new GeneralPath();
		path.moveTo(this.getX(), this.getY());
		
		int[] controlPoint = getControlPoint();
		
		int xControlPoint = controlPoint[0];
		int yControlPoint = controlPoint[1];
		path.quadTo(xControlPoint, yControlPoint, this.getDeltaX1(), this.getDeltaY1());
		g2.draw(path);
	}
	
	private void paintCurvedArrow(Graphics2D g2) {
		Polygon arrow = getArrowInDeltaPositions();
		g2.fillPolygon(arrow);
	}
	
	private void paintCurvedLineText(Graphics2D g2) {
		g2.setFont(new Font("Arial", Font.BOLD, fontSize));
		g2.setColor(this.color);
		
		int[] controlPoint = getControlPoint(MachineCanvas.ARC_LINE_CONTROL_POINT_TEXT_OFFSET);		
		int xControlPoint = controlPoint[0];
		int yControlPoint = controlPoint[1];
		
		int width = g2.getFontMetrics().stringWidth(getText());
		int height = g2.getFontMetrics().getHeight();
		
		g2.drawString(getText(), xControlPoint - width / 2, yControlPoint + height / 4);
	}
	
	/*@Override
	public int getX() {
		return this.fromState.getX();
	}

	@Override
	public int getY() {
		return this.fromState.getY();
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
		return this.toState.getX();
	}

	@Override
	public int getY1() {
		return this.toState.getY();
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
	}*/

}
