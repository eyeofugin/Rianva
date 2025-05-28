package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import game.objects.Equipment;

public class ItemInfo extends GUIElement {

    private final Equipment equipment;


    public ItemInfo(Equipment equipment) {
        this.equipment = equipment;
        this.setSize(94, 80);
    }

    @Override
    public int[] render() {
        int yf = 0;
        int[] itemNamePixels = getTextLine(this.equipment.getName(), this.width, 8,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0, yf, this.width, 8, itemNamePixels, false);
        yf += 10;

        String statBonusString = this.equipment.getInfoStatBonus();
        if (!statBonusString.isEmpty()) {
            int[] statPixels = getTextLine(statBonusString, this.width, 8,
                    TextAlignment.LEFT, Color.VOID, Color.WHITE);
            fillWithGraphicsSize(0, yf, this.width, 8, statPixels, false);
            yf += 10;
        }
        int[] descriptionPixels = getTextBlock(this.equipment.getDescription(), this.width, Color.VOID, Color.WHITE);
        int height = descriptionPixels.length / this.width;
        this.fillWithGraphicsSize(0, yf, this.width, height, descriptionPixels, false);
        return pixels;
    }
}
