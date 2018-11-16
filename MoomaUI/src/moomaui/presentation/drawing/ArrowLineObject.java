package moomaui.presentation.drawing;

public interface ArrowLineObject extends LineObject, ArrowObject {
	int getDeltaX1();
	int getDeltaY1();
	boolean isCurved();
	void setIsCurved(boolean isCurved);
}
