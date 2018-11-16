package moomaui.presentation.drawing;

public interface TextObject extends GraphicObject {
	String getText();
	void setText(String text);
	int getFontSize();
	void setFontSize(int fontSize);
}
