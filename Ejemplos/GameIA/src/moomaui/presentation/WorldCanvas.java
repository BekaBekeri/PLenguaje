package moomaui.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.LinkedList;

import moomaui.domain.game.CharacterPlayer;
import moomaui.domain.game.Pawn;
import moomaui.domain.game.World;
import moomaui.presentation.drawing.DrawableCharacter;
import moomaui.presentation.drawing.DrawablePawn;
import moomaui.presentation.drawing.JDrawer;

public class WorldCanvas extends JDrawer{
	private static final long serialVersionUID = 1L;
	public MachinePanel machinePanel;
	private World world;
	
	private LinkedList<DrawablePawn> pawns = new LinkedList<>();
	
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
	
	public static final Color VISION_CONE_COLOR = new Color(0, 0, 0, 127);
	public static final Color OUTSIDE_CIRCLE_STATE_DEFAULT = new Color(95, 95, 95);
	public static final Color STATE_ID_TEXT = Color.BLACK;
	public static final Color INSIDE_CIRCLE_STATE_DEFAULT = Color.RED;
	public static final Color INSIDE_CIRCLE_STATE_SELECTED = Color.GREEN;
	public static final Color INSIDE_CIRCLE_STATE_CANDIDATE = Color.CYAN;
	
	public WorldCanvas(World world) {
		this.world = world;		
		
		for (int i = 0; i < world.getPawnsInWorld().size(); i++) {
			Pawn pawn = world.getPawnsInWorld().get(i);
			
			DrawablePawn newPawn;
			if (pawn instanceof CharacterPlayer)
				newPawn = new DrawableCharacter((CharacterPlayer) pawn);
			else
				newPawn = new DrawablePawn(pawn);
			
			pawns.add(newPawn);
			pawn.addDeathListener(this::onPawnDeath);
			this.addGraphicObject(newPawn);
		}
	}
	
	@Override
	public void paint(Graphics g){		
		super.paint(g);
	}
	
	private void onPawnDeath(Pawn deadPawn, Pawn killerPawn) {
		machinePanel.logDeath(deadPawn, killerPawn);
		pawns.removeIf((DrawablePawn pawn) -> pawn.getID() == deadPawn.getId());
		world.kill(deadPawn);
		
		if (deadPawn instanceof CharacterPlayer) {
			for (Component comp : machinePanel.pnlControls.getComponents()) {
				comp.setEnabled(false);
			}
		}
	}
		
	public void updatePawns() {
		pawns.forEach((DrawablePawn pawn) -> pawn.update(world));
		repaint();
	}
	
	public World getWorld() {
		return this.world;
	}

	public LinkedList<DrawablePawn> getPawns() {
		return pawns;
	}
	
	public DrawablePawn getPawnInPosition(int x, int y) {
		DrawablePawn state = null;
		double best = Double.MAX_VALUE;
		
		for (DrawablePawn st : this.pawns) {
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
