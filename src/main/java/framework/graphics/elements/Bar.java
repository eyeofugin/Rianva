package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.logic.Stat;

public class Bar extends GUIElement {

    protected Hero hero;
    protected Stat resource;
    protected Color color;
    protected boolean withString = false;
    protected boolean withIcon = false;

    @Override
    public int[] render() {
        background(Color.VOID);
        int[] barPixels = getBar(this.width, this.height, 0, this.hero.getResourcePercentage(resource), color, Color.DARKRED);
        if (withString) {
            int[] barStringPixels = getTextLine(this.hero.getResourceString(resource), this.width, this.height, Color.WHITE);
            fill(barStringPixels);
        }
        fill(barPixels);
        if (withIcon) {
            int[] iconPixels = editor.getIcon(resource.getIconKey());
            fillWithGraphicsSize(2,(this.height-editor.charHeight)/2, editor.charWidth, editor.charHeight,iconPixels,false);
        }
        return pixels;
    }

    public Bar setResource(Stat resource) {
        this.resource = resource;
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Bar setHero(Hero hero) {
        this.hero = hero;
        return this;
    }

    public Bar setWithString(boolean withString) {
        this.withString = withString;
        return this;
    }

    public Bar setWithIcon(boolean withIcon) {
        this.withIcon = withIcon;
        return this;
    }
}
