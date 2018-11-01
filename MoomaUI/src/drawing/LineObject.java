package drawing;

public interface LineObject extends GraphicObject {
	int getX1();
	int getY1();
	int getStroke();
	void setX1(int X1);
	void setY1(int Y1);
	void setStroke(int stroke);
}
