package moomaui.presentation.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

import moomaui.domain.game.Pawn;
import moomaui.domain.game.World;
import moomaui.domain.game.Character;
import moomaui.presentation.WorldCanvas;

public class DrawableCharacter extends DrawablePawn {
	protected double angle;
	protected double visionCone;
	protected double visionRadius;
	
	
	public DrawableCharacter(Character ch) {
		super((Pawn) ch);
		angle = ch.getAngle();
		visionCone = Character.VISION_CONE;
		visionRadius = Character.VISION_LENGTH;
		
		innerColor = Color.GREEN;
	}
	
	@Override
	public void paint(Graphics2D g2) {
		paintArcSection(g2);
		super.paint(g2);
	}
	
	private void paintArcSection(Graphics2D g2) {
		g2.setColor(WorldCanvas.VISION_CONE_COLOR);
		Arc2D p = new Arc2D.Double(Arc2D.CHORD);
		p.setArcByCenter(x, y, visionRadius, Math.toDegrees(angle - visionCone / 2), Math.toDegrees(visionCone), Arc2D.PIE);
		g2.fill(p);
	}
	
	@Override
	public void update(World world) {
		super.update(world);
		Character ch = (Character) world.getPawnFromId(id);
		
		angle = ch.getAngle();	
	}
}
