package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import game.skills.Skill;

public class SkillInfo extends GUIElement {
    private static final int FULL_WIDTH = 200;
    private static final int HALF_WIDTH = FULL_WIDTH / 2;
    private static final int FIFTH = FULL_WIDTH / 5;
    private static final int TWO_FIFTH = FIFTH * 2;
    private static final int THREE_FIFTH = FIFTH * 3;
    private static final int FOUR_FIFTH = FIFTH * 4;
    private static final int ELEMENT_HEIGHT = 10;
    private final Skill skill;

    private int tempY = 0;

    public SkillInfo(Skill skill) {
        this.skill = skill;
        this.setSize(200, 88);
    }
    @Override
    public int[] render() {
        tempY = 0;
        //first row
        int[] skillNamePixels = getTextLine(this.skill.getName(), THREE_FIFTH, ELEMENT_HEIGHT,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0, tempY, THREE_FIFTH, ELEMENT_HEIGHT, skillNamePixels, false);

        String targetString = skill.getTargetString();
        int[] targetStringPixels = getTextLine(targetString, TWO_FIFTH, ELEMENT_HEIGHT,
                TextAlignment.RIGHT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(THREE_FIFTH, tempY,TWO_FIFTH, ELEMENT_HEIGHT, targetStringPixels, false);

        //second row
        int multipliers = skill.getDmgMultipliers().size() + skill.getHealMultipliers().size() + skill.getShieldMultipliers().size();
        int leftWidth = multipliers > 2? FOUR_FIFTH : THREE_FIFTH;
        int rightWidth = leftWidth == FOUR_FIFTH ? FIFTH : TWO_FIFTH;

        tempY += ELEMENT_HEIGHT + 5;

        String costString = skill.getCostString();
        int[] costPixels = getTextLine(costString, rightWidth, ELEMENT_HEIGHT,
                TextAlignment.RIGHT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(leftWidth, tempY, rightWidth, ELEMENT_HEIGHT, costPixels, false);

        boolean hasDhs = false;
        hasDhs = printDhsLine(skill.getDmgStringGUI(), leftWidth);
        hasDhs = printDhsLine(skill.getHealStringGUI(), leftWidth) || hasDhs;
        hasDhs = printDhsLine(skill.getShieldStringGUI(), leftWidth) || hasDhs;

        if (!hasDhs) {
            tempY += ELEMENT_HEIGHT + 5;
        }

        //effect blocks
        printDescriptionBlock(skill.getFaithGainString());
        printDescriptionBlock(skill.getEffectString());
        printDescriptionBlock(skill.getCasterEffectString());
        printDescriptionBlock(this.skill.getUpperDescriptionFor(this.skill.hero));
        printDescriptionBlock(this.skill.getDescriptionFor(this.skill.hero));

        return pixels;
    }

    private boolean printDhsLine(String line, int width) {
        if (line.isEmpty()) {
            return false;
        }
        int[] descriptionPixels = getTextLine(line, width, ELEMENT_HEIGHT,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0, tempY, width, ELEMENT_HEIGHT, descriptionPixels, false);
        tempY += ELEMENT_HEIGHT + 5;
        return true;
    }
    private void printDescriptionBlock(String block) {
        if (block.isEmpty()) {
            return;
        }
        int[] descriptionPixels = getTextBlock(block, this.width, Color.VOID, Color.WHITE);
        int height = descriptionPixels.length / this.width;
        fillWithGraphicsSize(0, tempY,  this.width, height, descriptionPixels, false);
        tempY += height + 5;
    }
}
