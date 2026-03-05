package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import game.skills.Skill;

public class SkillElement extends GUIElement {

  public static final int _WIDTH = 120;
  public static final int _HEIGHT = 20;

  private Skill skill;

  public SkillElement(Skill skill) {
    super(_WIDTH, _HEIGHT);
    this.skill = skill;
  }

  @Override
  public int[] render() {
    fillWithGraphicsSize(2, 2, 16, 16, skill.getIconPixels(), false);
    fillWithGraphicsSize(20, 2, 100, 16, getTextLine(skill.getName(), 100, 20, Color.WHITE), false);
    return pixels;
  }
}
