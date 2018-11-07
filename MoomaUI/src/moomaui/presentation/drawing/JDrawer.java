package moomaui.presentation.drawing;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import moomaui.presentation.MachineCanvas;

public class JDrawer extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6095924263041350419L;
	private ArrayList<GraphicObject> graphicObjectsHistory = new ArrayList<GraphicObject>();
	private ArrayList<GraphicObject> graphicObjects = new ArrayList<GraphicObject>();
	private int objectIndex = -1;
	private final static int FONT_SIZE = 15;
	public Action lastAction = Action.CREATE;
	
	private BufferedImage bImage = null;
	
	public void addGraphicObject(GraphicObject obj) {
		graphicObjects.add(obj);
		if (lastAction == Action.REDO || lastAction == Action.UNDO) {
			graphicObjectsHistory.clear();
			graphicObjectsHistory.addAll(graphicObjects);
		} else {
			graphicObjectsHistory.add(obj);
		}
		lastAction = Action.PAINT;
		objectIndex++;
	}
	
	public GraphicObject getLastGraphicObject() {
		if (objectIndex == -1 || graphicObjects.size() == 0) {
			return null;
		}
		return graphicObjects.get(graphicObjects.size() - 1);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		setAntialias(g);
		drawImage(g);		
	}
	
	private void setAntialias(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON);
	    rh.add(new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
	    g2.setRenderingHints(rh);
	}
	
	public void drawImage(Graphics g) {
		@SuppressWarnings("unchecked")
		ArrayList<GraphicObject> sortedGraphics = (ArrayList<GraphicObject>) graphicObjects.clone();
		Collections.sort(sortedGraphics, new Comparator<GraphicObject>() {
		    @Override
		    public int compare(GraphicObject o1, GraphicObject o2) {
		    	if (o1 instanceof LineObject && o2 instanceof LineObject) {
		    		return 0;
		    	} else if (o1 instanceof LineObject && !(o2 instanceof LineObject)) {
		    		return -1;
		    	} else if (o1 instanceof CircleObject && o2 instanceof LineObject) {
		    		return 1;
		    	} else if (o1 instanceof CircleObject && o2 instanceof CircleObject) {
		    		return 0;
		    	} else if (o1 instanceof CircleObject && o2 instanceof TextObject) {
		    		return -1;
		    	} else {
		    		return 1; 
		    	}
		    }
		});
		
		for (GraphicObject obj : sortedGraphics) {
			
			// Drawing lines
			if (obj instanceof ArcArrowLineObject) { // Draw line and arrow
				Graphics2D g2 = (Graphics2D) g;
				ArrowLineObject aObj = (ArrowLineObject) obj;
				g2.setStroke(new BasicStroke(((LineObject)obj).getStroke()));
				g2.setColor(((LineObject)obj).getColor());
				int[] midPoint = midPoint(aObj.getX(), aObj.getX1(), aObj.getY(), aObj.getY1());
				int radius = (int) euclideanDistance(aObj.getX(), midPoint[0], aObj.getY(), midPoint[1]);
				double angle = angleBetweenPoints(aObj.getX(), midPoint[0], aObj.getY(), midPoint[1]);
				/*double widthScale = Math.max(1, 2 * Math.cos(Math.toRadians(angle)));
				double heightScale = Math.max(1, 2 * Math.sin(Math.toRadians(angle)));
				System.out.format("Angle: %d   WidthScale: %f   HeightScale: %f\n", angle, widthScale, heightScale);*/
				GeneralPath path = new GeneralPath();
				path.moveTo(aObj.getX(), aObj.getY());
				int xControlPointOffset = (int) (Math.sin(angle) * MachineCanvas.ARC_LINE_CONTROL_POINT_OFFSET);
				int yControlPointOffset = (int) (Math.cos(angle) * MachineCanvas.ARC_LINE_CONTROL_POINT_OFFSET);
				System.out.println(xControlPointOffset + "   " + yControlPointOffset);
				path.quadTo(midPoint[0] - xControlPointOffset, midPoint[1] - yControlPointOffset, aObj.getX1(), aObj.getY1());
				g2.draw(path);
				//g2.drawArc(midPoint[0] - radius, midPoint[1] - radius, radius * 2, radius * 2, -angle, 180);

				/*int[][] coords = generateRegularPolygon(3, aObj.getArrowSize(), aObj.getOrientation());
				Polygon arrow = new Polygon();
				for (int[] point : coords) {
					arrow.addPoint(point[0] + aObj.getDeltaX1(), point[1] + aObj.getDeltaY1());
				}
				g2.fillPolygon(arrow);*/
			}
			else if (obj instanceof ArrowLineObject) { // Draw line and arrow
				Graphics2D g2 = (Graphics2D) g;
				ArrowLineObject aObj = (ArrowLineObject) obj;
				g2.setStroke(new BasicStroke(((LineObject)obj).getStroke()));
				g2.setColor(((LineObject)obj).getColor());
				g2.drawLine(obj.getX(), obj.getY(), aObj.getDeltaX1(), aObj.getDeltaY1());

				int[][] coords = generateRegularPolygon(3, aObj.getArrowSize(), aObj.getOrientation());
				Polygon arrow = new Polygon();
				for (int[] point : coords) {
					arrow.addPoint(point[0] + aObj.getDeltaX1(), point[1] + aObj.getDeltaY1());
				}
				g2.fillPolygon(arrow);
			}
			else if (obj instanceof LineObject) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(((LineObject)obj).getStroke()));
				g2.setColor(((LineObject)obj).getColor());
				g2.drawLine(obj.getX(), obj.getY(), ((LineObject) obj).getX1(), ((LineObject) obj).getY1());
			}
			
			// Drawing circles
			if (obj instanceof BevelCircleObject) {
				BevelCircleObject bObj = (BevelCircleObject) obj;
				int radius = bObj.getRadius();
				int offset = bObj.getOffset();
				
				g.setColor(bObj.getColor());
				g.fillOval(bObj.getX() - radius, bObj.getY() - radius, radius * 2, radius * 2);
				
				g.setColor(bObj.getInnerColor());
				g.fillOval(bObj.getX() + offset / 2 - radius, bObj.getY() + offset / 2 - radius, radius * 2 - offset, radius * 2 - offset);
			}
			else if (obj instanceof CircleObject) {
				g.setColor(((CircleObject)obj).getColor());
				int radius = ((CircleObject)obj).getRadius();
				g.setColor(Color.BLACK);
				g.fillOval(obj.getX(), obj.getY(), radius * 2, radius * 2);
				g.setColor(Color.GRAY);
				g.fillOval(obj.getX() + 3, obj.getY() + 3, radius * 2 - 6, radius * 2 - 6);
			}
			
			// Drawing text
			if (obj instanceof TextObject && obj instanceof LineObject) {
				LineObject lObj = (LineObject) obj;
				g.setFont(new Font("Arial", Font.BOLD, 15));
				g.setColor(((TextObject) obj).getColor());
				int width = g.getFontMetrics().stringWidth(((TextObject) obj).getText());
				int height = g.getFontMetrics().getHeight();
				
				int[] midPoint = midPoint(lObj.getX(), lObj.getX1(), lObj.getY(), lObj.getY1());
				g.drawString(((TextObject) obj).getText(), midPoint[0] - width / 2, midPoint[1] + height / 4);
			}
			else if (obj instanceof TextObject) {
				g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
				int width = g.getFontMetrics().stringWidth(((TextObject) obj).getText());
				int height = g.getFontMetrics().getHeight();
				
				g.setColor(((TextObject) obj).getColor());
				g.drawString(((TextObject) obj).getText(), obj.getX() - width / 2, obj.getY() + height / 4);
			}
		}
	}
	
	public void save(File route) throws IOException {
		Image ogImage = null;
		ogImage = ImageIO.read(route);
		
		bImage = new BufferedImage(ogImage.getWidth(null), ogImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bImage.createGraphics();
		g2d.drawImage(ogImage, 0, 0, null);

		drawImage(g2d);
		
		
		String extension = "";
		int i = route.getAbsolutePath().lastIndexOf('.');
		if (i > 0) {
		    extension = route.getAbsolutePath().substring(i+1);
		}
		
		ImageIO.write(bImage, extension.equals("") ? "PNG" : extension, route);
	}

	public boolean canUndo() {
		return graphicObjects.size() > 0;
	}
	
	public boolean canRedo() {
		return graphicObjects.size() < graphicObjectsHistory.size();
	}
	
	public void undo() {
		if (canUndo()) {
			graphicObjects.remove(objectIndex--);
			this.lastAction = Action.UNDO;
			this.repaint();
		}
	} 
	
	public void redo() {
		if (canRedo()) {
			objectIndex++;
			graphicObjects.add(objectIndex, graphicObjectsHistory.get(objectIndex));
			this.lastAction = Action.REDO;
			this.repaint();
		}
	}
	
	public static int[][] generateRegularPolygon(int sides, int radius, double degreeOffset) {
		double alpha = 2 * Math.PI / sides;
		int[][] coords = new int[sides][];
		for (int i = 0; i < sides; i++) {
			int x = (int) (Math.cos(alpha * i + degreeOffset) * radius);
		    int y = (int) (Math.sin(alpha * i + degreeOffset) * radius);
		    coords[i] = new int[] {x, y};
		}
		
		return coords;
	}
	
	public static int[][] generateRegularPolygon(int sides, int radius) {
		return generateRegularPolygon(sides, radius, Math.PI);
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
	
	public static int[] midPoint(int x1, int x2, int y1, int y2) {
		return new int[] {(x2 + x1) / 2, (y2 + y1) / 2};
	}
	
	// p0 is the center of the arc, p1 is the source state, p2 is the destination state
	public static int[] getArcAngles(int x0, int x1, int x2, int y0, int y1, int y2) {
		int startAngle = (int) (180/Math.PI*Math.atan2(y1-y0, x1-x0));
		int endAngle = (int) (180/Math.PI*Math.atan2(y2-y0, x2-x0));
		return new int[] {startAngle, endAngle};
	}
}

enum Action {
	CREATE, PAINT, UNDO, REDO
}
