package moomaui.domain;

import java.awt.Color;

import moomaui.presentation.MachineCanvas;
import moomaui.presentation.drawing.BevelCircleObject;
import moomaui.presentation.drawing.TextObject;

public class DrawableState extends State implements BevelCircleObject, TextObject {
	private int x;
	private int y;
	private Color color = Color.BLACK;
	private Color innerColor = Color.LIGHT_GRAY;
	private int radius = MachineCanvas.STATE_RADIUS;
	private int offset = MachineCanvas.STATE_OFFSET;
	
	public DrawableState(String name) {
		super(name);
		this.x = -10;
		this.y = -10;
	}
	
	public DrawableState(State st) {
		this(st.getName());
	}
	
	public int getX() {
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
	}
}
