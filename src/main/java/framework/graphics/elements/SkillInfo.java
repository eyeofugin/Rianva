package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.resources.SpriteLibrary;
import game.skills.Skill;
import game.skills.logic.SkillTag;

public class SkillInfo extends GUIElement {
  private static final int FULL_WIDTH = 200;
  private static final int HALF_WIDTH = FULL_WIDTH / 2;
  private static final int FIFTH = FULL_WIDTH / 5;
  private static final int TWO_FIFTH = FIFTH * 2;
  private static final int THREE_FIFTH = FIFTH * 3;
  private static final int FOUR_FIFTH = FIFTH * 4;
  private static final int ELEMENT_HEIGHT = 10;
  private static final int DMG_TYPE_FROM = 100;
  private static final int DMG_MODE_FROM = 110;
  private static final int ICON_DIMENSION = 10;
  private static final int HEADER_Y = 0;
  private static final int SUBHEADER_Y = 15;
  private static final int TAG_Y = 30;
  private static final int DESCRIPTION_Y = 45;
  private static final int COST_WIDTH = 25;
  private static final int CD_WIDTH = 25;
  private static final int ACCURACY_WIDTH = 30;

  private final Skill skill;

  public SkillInfo(Skill skill) {
    this.skill = skill;
    this.setSize(200, 88);
  }

  private void renderHeader() {
    int[] skillNamePixels =
        getTextLine(
            this.skill.getName(),
            THREE_FIFTH,
            ELEMENT_HEIGHT,
            TextAlignment.LEFT,
            Color.VOID,
            Color.WHITE);
    fillWithGraphicsSize(0, HEADER_Y, THREE_FIFTH, ELEMENT_HEIGHT, skillNamePixels, false);

    String targetString = skill.getTargetString();
    int[] targetStringPixels =
        getTextLine(
            targetString, TWO_FIFTH, ELEMENT_HEIGHT, TextAlignment.RIGHT, Color.VOID, Color.WHITE);
    fillWithGraphicsSize(
        THREE_FIFTH, HEADER_Y, TWO_FIFTH, ELEMENT_HEIGHT, targetStringPixels, false);
  }

  private void renderSubHeader() {
    renderDmgHealShield();
    renderSkillStats();
  }

  private void renderSkillStats() {
    int tempX = THREE_FIFTH;
    int[] costPixels =
        getTextLine(
            this.skill.getCostStringGUI(),
            COST_WIDTH,
            ELEMENT_HEIGHT,
            TextAlignment.CENTER,
            Color.VOID,
            this.skill.getCostColor());
    String cdString = "";
    if (this.skill.getCurrentCd() > 1) {
      cdString += Color.RED.getCodeString();
      cdString += this.skill.getCurrentCd();
      cdString += Color.WHITE.getCodeString() + " " ;
    }
    cdString += this.skill.getMaxCd();
    int[] cdPixels =
        getTextLine(
            cdString,
            CD_WIDTH,
            ELEMENT_HEIGHT,
            TextAlignment.CENTER,
            Color.VOID,
            Color.WHITE);
    int[] accuracyPixels =
        getTextLine(
            this.skill.getAccuracy() + "",
            ACCURACY_WIDTH,
            ELEMENT_HEIGHT,
            TextAlignment.CENTER,
            Color.VOID,
            Color.WHITE);
    fillWithGraphicsSize(tempX, SUBHEADER_Y, COST_WIDTH, ELEMENT_HEIGHT, costPixels, false);
    tempX += COST_WIDTH;
    fillWithGraphicsSize(tempX, SUBHEADER_Y, CD_WIDTH, ELEMENT_HEIGHT, cdPixels, false);
    tempX += CD_WIDTH;
    fillWithGraphicsSize(tempX, SUBHEADER_Y, ACCURACY_WIDTH, ELEMENT_HEIGHT, accuracyPixels, false);
  }

  private void renderDmgHealShield() {
    String dmgString = this.skill.getDmgStringGUI();
    String healString = this.skill.getHealStringGUI();
    String shieldString = this.skill.getShieldStringGUI();
    if (!dmgString.isEmpty()) {
      printDhsLine(dmgString, THREE_FIFTH, 0);
      printDmgTypeAndMode();
    }
    if (!healString.isEmpty()) {
      int width = shieldString.isEmpty() ? THREE_FIFTH : THREE_FIFTH / 2;
      printDhsLine(healString, width, 0);
    }
    if (!shieldString.isEmpty()) {
      int width = healString.isEmpty() ? THREE_FIFTH : THREE_FIFTH / 2;
      int xFrom = healString.isEmpty() ? 0 : THREE_FIFTH / 2;
      printDhsLine(shieldString, width, xFrom);
    }
  }

  private void printDhsLine(String line, int width, int xFrom) {
    int[] descriptionPixels =
        getTextLine(line, width, ELEMENT_HEIGHT, TextAlignment.LEFT, Color.VOID, Color.WHITE);
    fillWithGraphicsSize(xFrom, SUBHEADER_Y, width, ELEMENT_HEIGHT, descriptionPixels, false);
  }

  private void printDmgTypeAndMode() {
    int[] typePixels = SpriteLibrary.getSprite(this.skill.getDamageType().name());
    int[] modePixels = SpriteLibrary.getSprite(this.skill.getDamageMode().name());
    fillWithGraphicsSize(
        DMG_TYPE_FROM, SUBHEADER_Y, ICON_DIMENSION, ICON_DIMENSION, typePixels, false);
    fillWithGraphicsSize(
        DMG_MODE_FROM, SUBHEADER_Y, ICON_DIMENSION, ICON_DIMENSION, modePixels, false);
  }

  private void renderTags() {
    String tags = String.join(" ", skill.tags.stream().map(SkillTag::name).toList());
    int[] tagsPixels = getTextLine(tags, FULL_WIDTH, ELEMENT_HEIGHT, TextAlignment.LEFT, Color.VOID, Color.DARKGREY);
    fillWithGraphicsSize(0, TAG_Y, FULL_WIDTH, ELEMENT_HEIGHT, tagsPixels, false);
  }

  private void renderDescription() {
    int[] descriptionPixels = getTextBlock(skill.getDescription(), FULL_WIDTH, Color.VOID, Color.WHITE);
    int height = descriptionPixels.length / this.width;
    fillWithGraphicsSize(0, DESCRIPTION_Y, FULL_WIDTH, height, descriptionPixels, false);
  }

  @Override
  public int[] render() {
    renderHeader();
    if (!this.skill.isPassive()) {
      renderSubHeader();
    }
    renderTags();
    renderDescription();
    return pixels;
  }

}
