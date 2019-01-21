package moomaui.presentation.drawing;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
			obj.paint((Graphics2D) g);
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
