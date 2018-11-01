package moomaui.drawing;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class JDrawer extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6095924263041350419L;
	private ArrayList<GraphicObject> graphicObjectsHistory = new ArrayList<GraphicObject>();
	private ArrayList<GraphicObject> graphicObjects = new ArrayList<GraphicObject>();
	private int objectIndex = -1;
	public Action lastAction = Action.CREATE;
	private static final int FONT_OFFSET = 5;
	
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
	
	public void paint(Graphics g){
		super.paint(g);
		drawImage(g);		
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
			if (obj instanceof ArrowLineObject) {
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
				g2.fillPolygon(arrow);;
			}
			else if (obj instanceof LineObject) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(((LineObject)obj).getStroke()));
				g2.setColor(((LineObject)obj).getColor());
				g2.drawLine(obj.getX(), obj.getY(), ((LineObject) obj).getX1(), ((LineObject) obj).getY1());
			}
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
			if (obj instanceof TextObject && obj instanceof LineObject) {
				LineObject lObj = (LineObject) obj;
				g.setFont(new Font("Arial", Font.BOLD, 15));
				g.setColor(((TextObject) obj).getColor());
				int[] midPoint = midPoint(lObj.getX(), lObj.getX1(), lObj.getY(), lObj.getY1());
				g.drawString(((TextObject) obj).getText(), midPoint[0], midPoint[1]);
			}
			else if (obj instanceof TextObject) {
				g.setFont(new Font("Arial", Font.BOLD, 15));
				g.setColor(((TextObject) obj).getColor());
				g.drawString(((TextObject) obj).getText(), obj.getX() - FONT_OFFSET / 2, obj.getY() + FONT_OFFSET);
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
	
	public static int[] midPoint(int x1, int x2, int y1, int y2) {
		return new int[] {(x2 + x1) / 2, (y2 + y1) / 2};
	}
}

enum Action {
	CREATE, PAINT, UNDO, REDO
}
