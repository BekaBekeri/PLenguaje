package drawing;

import java.awt.Color;

public interface BevelCircleObject extends CircleObject {
	int getOffset();
	void setOffset(int offset);
	Color getInnerColor();
	void setInnerColor(Color innerColor);
}
