package drawing;

import java.awt.Color;

public interface GraphicObject {
	int getX();
	int getY();
	Color getColor();
	void setX(int X);
	void setY(int Y);
	void setColor(Color color);
}
