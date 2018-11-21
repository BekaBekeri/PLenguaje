package moomaui.presentation.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;

import moomaui.presentation.MachineCanvas;

public class DrawableState implements BevelCircleObject, TextObject {
	private String name;
	private boolean isInitial;

	private int x;
	private int y;
	private Color color = MachineCanvas.OUTSIDE_CIRCLE_STATE_DEFAULT;
	private Color innerColor = MachineCanvas.INSIDE_CIRCLE_STATE_DEFAULT;
	private int radius = MachineCanvas.STATE_RADIUS;
	private int offset = MachineCanvas.STATE_OFFSET;
	private int fontSize = MachineCanvas.FONT_SIZE;
	
	public DrawableState(String name) {
		this.name = name;
		this.x = -10;
		this.y = -10;
	}
	
	@Override
	public int getRadius() {
		return this.radius;
	}

	@Override
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}
	
	public boolean isInitial() {
		return isInitial;
	}

	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setX(int X) {
		this.x = X;
	}

	@Override
	public void setY(int Y) {
		this.y = Y;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String getText() {
		return this.name;
	}

	@Override
	public void setText(String text) {
		this.name = text;
	}

	@Override
	public int getOffset() {
		return this.offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public Color getInnerColor() {
		return this.innerColor;
	}

	@Override
	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	@Override
	public int getFontSize() {
		return this.fontSize;
	}

	@Override
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@Override
	public void paint(Graphics2D g2) {
		if (isInitial)
			paintInitialArrow(g2);
		
		paintCircles(g2);
		paintText(g2);
		
	}
	
	private void paintCircles(Graphics2D g2) {		
		g2.setColor(color);
		g2.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		
		g2.setColor(innerColor);
		g2.fillOval(x + offset / 2 - radius, y + offset / 2 - radius, radius * 2 - offset, radius * 2 - offset);
	}
	
	private void paintText(Graphics2D g2) {
		g2.setFont(new Font("Arial", Font.BOLD, fontSize));
		int width = g2.getFontMetrics().stringWidth(name);
		int height = g2.getFontMetrics().getHeight();
		
		g2.setColor(color);
		g2.drawString(name, x - width / 2, y + height / 4);
	}
	
	private void paintInitialArrow(Graphics2D g2) {
		int[][] coords = MachineCanvas.generateRegularPolygon(3, MachineCanvas.INITIAL_ARROW_SIZE, 0);
		Polygon arrow = new Polygon();
		for (int[] point : coords) {
			arrow.addPoint(point[0] + this.getX() - this.getRadius() - MachineCanvas.INITIAL_ARROW_SIZE + 1, point[1] + this.getY());
		}
		g2.fillPolygon(arrow);
	}

	public void setCoords(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public void setCoords(int[] coords) {
		setCoords(coords[0], coords[1]);
	}

	
	public boolean equals(Object obj) {
		return (obj instanceof DrawableState && ((DrawableState)obj).getText().equals(this.name));
	}
	
	/*public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public void setCoords(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public void setCoords(int[] coords) {
		setCoords(coords[0], coords[1]);
	}

	@Override
	public int getRadius() {
		return this.radius;
	}

	@Override
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public Color getInnerColor() {
		return this.innerColor;
	}

	@Override
	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;		
	}

	@Override
	public String getText() {
		return this.name;
	}

	@Override
	public void setText(String text) {
		this.name = text;
	}*/
}
