package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import game.skills.logic.Effect;

public class EffectInfo extends GUIElement {

    private final Effect effect;

    public EffectInfo(Effect effect) {
        this.effect = effect;
        this.setSize(94,80);
    }

    @Override
    public int[] render() {
        int yf = 0;
        int[] effectNamePixels = getTextLine(this.effect.name, this.width, 8,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0,yf,this.width,8,effectNamePixels,false);
        String detail = this.effect.getDetailInfo();
        if (!detail.isBlank()) {
            int[] detailPixels = getTextLine(detail, this.width, 8, TextAlignment.RIGHT, Color.VOID, Color.WHITE);
            fillWithGraphicsSize(0, yf, this.width, 8, detailPixels, false);
        }
        yf += 8;

        if (this.effect.origin != null) {
            int[] originPixels = getTextLine("From:" + this.effect.origin.getName(), this.width, 8,
                    TextAlignment.LEFT, Color.VOID, Color.WHITE);
            fillWithGraphicsSize(0, yf, this.width, 8, originPixels, false);
            yf += 8;
        }
        yf += 4;

        int[] descriptionPixels = getTextBlock(this.effect.description, this.width, Color.VOID, Color.WHITE);
        int height = descriptionPixels.length / this.width;
        fillWithGraphicsSize(0, yf, this.width, height, descriptionPixels, false);
        return pixels;
    }
}
