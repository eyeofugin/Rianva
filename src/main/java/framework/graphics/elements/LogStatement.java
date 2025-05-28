package framework.graphics.elements;

import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;

public class LogStatement extends GUIElement {

    private final int paddingX = 2;
    private final int paddingY = 2;
    private final int textWidth = Property.LOG_STATEMENT_WIDTH - (2 * paddingX);
    private final int textHeight;
    private final int[] textPixels;

    public LogStatement(String text) {
        this.textPixels = this.getTextBlock(text, textWidth, Color.BLACK, Color.WHITE);
        this.textHeight = textPixels.length / textWidth;
        this.width = Property.LOG_STATEMENT_WIDTH;
        this.height = textHeight + (2 * paddingY);
        this.pixels = new int[this.width * this.height];
    }

    public int[] render() {
        fillWithGraphicsSize(paddingX, paddingY, textWidth, textHeight, textPixels, false);
        GUIElement.addBorder(this.width, this.height, this.pixels,Color.WHITE);
        return this.pixels;
    }
}
