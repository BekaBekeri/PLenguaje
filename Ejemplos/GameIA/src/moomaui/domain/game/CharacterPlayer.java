package moomaui.domain.game;

public class CharacterPlayer extends Pawn {
	public static final double VISION_CONE = Math.toRadians(55);
	public static final int VISION_LENGTH = 250;
	public static final int SPEED = 25;
	
	private double angle;

	public double getAngle() {
		return angle;
	}

	public void setAngle(double looking) {
		this.angle = looking;
		
		// Normalize angle
		this.angle = this.angle - (Math.PI * 2) * Math.floor((this.angle + Math.PI) / (Math.PI * 2));
	}
	
	public void moveInDirection(Direction[] directions) {
		
		int x = 0; int y = 0;
		for (Direction dir : directions) {
			switch (dir) {
			case UP:
				y += -1;
				break;
			case DOWN:
				y += 1;
				break;
			case RIGHT:
				x += 1;
				break;
			case LEFT:
				x += -1;
				break;
			}
		}
		
		if (directions.length == 0 || (x == 0 && x == y))
			return;
		
		double rads = Math.atan2(y,  x);
		this.x += Math.cos(rads) * SPEED;
		this.y += Math.sin(rads) * SPEED;
	}
}
