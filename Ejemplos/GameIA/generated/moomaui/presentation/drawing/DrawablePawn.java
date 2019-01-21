package moomaui.presentation.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

import moomaui.domain.game.Pawn;
import moomaui.domain.game.World;
import moomaui.presentation.WorldCanvas;

public class DrawablePawn implements BevelCircleObject, TextObject {
	protected int id;
	protected int x;
	protected int y;
	protected int health;
	
	private String name;

	protected Color color = WorldCanvas.OUTSIDE_CIRCLE_STATE_DEFAULT;
	protected Color textColor = WorldCanvas.STATE_ID_TEXT;
	protected Color innerColor = WorldCanvas.INSIDE_CIRCLE_STATE_DEFAULT;
	private int radius = WorldCanvas.STATE_RADIUS;
	private int offset = WorldCanvas.STATE_OFFSET;
	private int fontSize = WorldCanvas.FONT_SIZE;
	
	public DrawablePawn(Pawn pawn) {
		this.id = pawn.getId();
		this.x = pawn.getX();
		this.y = pawn.getY();
		this.health = pawn.getHealth();
		
		this.name = Integer.toString(id);
	}
	
	public int getID() {
		return id;
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
		return innerColor;
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
	
	public void update(World world) {
		Pawn pawn = world.getPawnFromId(id);
		
		x = pawn.getX();
		y = pawn.getY();
		health = pawn.getHealth();		
	}

	@Override
	public void paint(Graphics2D g2) {		
		paintCircles(g2);
		paintText(g2);		
	}
	
	private void paintCircles(Graphics2D g2) {		
		g2.setColor(color);
		g2.fillOval(x - radius, y - radius, radius * 2, radius * 2);

		g2.setColor(getInnerColor());
		Arc2D p = new Arc2D.Double(Arc2D.CHORD);
		p.setArcByCenter(x, y, radius - offset + 2, 270 - 180 * health / 100, 360 * health / 100, Arc2D.CHORD);
		g2.fill(p);
	}
	
	private void paintText(Graphics2D g2) {
		g2.setFont(new Font("Arial", Font.BOLD, fontSize));
		int width = g2.getFontMetrics().stringWidth(Integer.toString(this.id));
		int height = g2.getFontMetrics().getHeight();
		
		g2.setColor(textColor);
		g2.drawString(Integer.toString(this.id), x - width / 2, y + height / 4);
	}

	public void setCoords(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public void setCoords(int[] coords) {
		setCoords(coords[0], coords[1]);
	}

	
	public boolean equals(Object obj) {
		return (obj instanceof DrawablePawn && ((DrawablePawn)obj).getText().equals(this.name));
	}	
}
