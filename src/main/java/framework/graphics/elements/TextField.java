package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;

public class TextField extends GUIElement {
  protected String string;
  protected int textSize;
  protected Color fontColor;
  protected boolean small;
  protected TextAlignment alignment = TextAlignment.LEFT;

  @Override
  public int[] render() {
    if (!small) {
      fill(getTextLine(string, width, height, alignment, Color.VOID, fontColor));
    } else {
      fill(getSmallNumTextLine(string, width, height, alignment, Color.VOID, fontColor));
    }
    return pixels;
  }

  public void setString(String string) {
    this.string = string;
  }

  public void setTextSize(int textSize) {
    this.textSize = textSize;
  }

  public void setFontColor(Color fontColor) {
    this.fontColor = fontColor;
  }

  public void setSmall(boolean small) {
    this.small = small;
  }

  public void setAlignment(TextAlignment alignment) {
    this.alignment = alignment;
  }
}
