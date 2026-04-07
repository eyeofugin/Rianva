package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import game.entities.Hero;
import game.entities.HeroDTO;
import game.skills.logic.Stat;

public class StatField extends GUIElement {
  private final Hero hero;
  private final HeroDTO heroDTO;
  private Stat[] leftStatArray =
      new Stat[] {
        Stat.MIND,
        Stat.BODY,
        Stat.DEXTERITY,
        Stat.VITALITY,
        Stat.DODGE,
        Stat.LETHALITY,
        Stat.CRIT_CHANCE
      };
  private Stat[] rightStatArray =
      new Stat[] {
        Stat.ARMOR,
        Stat.HEAT_RESIST,
        Stat.COLD_RESIST,
        Stat.DARK_RESIST,
        Stat.LIGHT_RESIST,
        Stat.MENTAL_RESIST,
        Stat.TOX_RESIST,
        Stat.SHOCK_RESIST
      };

  public StatField(HeroDTO dto) {
    this.heroDTO = dto;
    this.hero = null;
    this.setSize(200, 150);
  }
  public StatField(Hero hero) {
    this.hero = hero;
    this.heroDTO = null;
    this.setSize(200, 150);
  }
  public StatField(HeroDTO dto, Stat[] lArray, Stat[] rArray) {
    this.heroDTO = dto;
    this.hero = null;
    this.setSize(200, 150);
    this.leftStatArray = lArray;
    this.rightStatArray = rArray;
  }
  public StatField(Hero hero, Stat[] lArray, Stat[] rArray) {
    this.hero = hero;
    this.heroDTO = null;
    this.setSize(200, 150);
    this.leftStatArray = lArray;
    this.rightStatArray = rArray;
  }

  @Override
  public int[] render() {
    background(Color.VOID);
    renderSide(leftStatArray, 3);
    renderSide(rightStatArray, this.width / 2);
    return pixels;
  }

  private void renderSide(Stat[] stats, int x) {
    int yf = 3;
    for (Stat stat : stats) {
      int totalValue = hero != null? hero.getStat(stat) : heroDTO.getStat(stat);
      int statChange = hero != null?hero.getStatChange(stat) :0;
      int baseValue = totalValue - statChange;
      //            if (baseValue != 100 && (statChange != 0 || baseValue != 0)) {
      String baseStatString = stat.getReference() + ":" + baseValue;
      String statChangeString = "";
      if (statChange != 0) {
        statChangeString = statChange > 0 ? "{006}+" : "{012}";
        statChangeString += statChange;
        statChangeString += "{000}";
      }
      baseStatString += statChangeString;

      int[] baseStatStringPixels =
          getTextLine(
              baseStatString, this.width / 2 - 5, 8, TextAlignment.LEFT, Color.VOID, Color.WHITE);
      fillWithGraphicsSize(x, yf, this.width / 2 - 5, 8, baseStatStringPixels, false);

      yf += 9;
      //            }

    }
  }
}
