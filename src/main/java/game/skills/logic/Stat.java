package game.skills.logic;

import framework.graphics.text.Color;

import java.util.List;
import java.util.Random;

public enum Stat {
  //Offensive stats
  MIND("Mind", "MIN", "{013}"),
  BODY("Body", "BOD", "{014}"),
  DEXTERITY("Dexterity", "DEX", "{015}"),

  //Defensive Stats
  VITALITY("Vitality", "VIT", "{009}"),// x3 is the life value
  HEAT_RESIST("Heat Resist", "SHI", "{008}"),
  COLD_RESIST("Cold Resist", "SHI", "{008}"),
  LIGHT_RESIST("Light Resist", "SHI", "{008}"),
  DARK_RESIST("Dark Resist", "SHI", "{008}"),
  SHOCK_RESIST("Shock Resist", "SHI", "{008}"),
  MENTAL_RESIST("Mental Resist", "SHI", "{008}"),
  TOX_RESIST("Tox Resist", "SHI", "{008}"),

  //Utility Stats
  ENERGY("Max", "NRG", "{011}"),
  FOCUS("Focus", "NRR", "{011}"), // /10 is the energy regain value

  //Equipment Source stats
  LETHALITY("Lethality","LET", "{001}"),
  ARMOR("Armor", "ARM", "{008}"),
  CRIT_CHANCE("Crit Chance", "CRI", "{001}"),
  LIFE_REGAIN("Regain", "LRE", "{009}"),

  //Resource Stats
  LIFE("Life", "LIF", "{009}"),
  CURRENT_LIFE("Life", "CLI", "{009}"),
  CURRENT_ENERGY("Energy", "CNR", "{011}"),
  ENERGY_REGAIN("Regain", "NRR", "{011}"),
  DODGE("Dodge", "DDG", "{001}"),
  ACCURACY("Accuracy", "ACC", "{001}"),
  SHIELD("Shield", "SHI", "{008}");

  //    MAX_ACTION("Max", "ACT", "{001}"),
  //    CURRENT_ACTION("Action", "CAC", "{001}");

  private final String translationString;
  private final String iconKey;
  private final String colorKey;
  public static List<Stat> resistances = List.of(HEAT_RESIST, COLD_RESIST, SHOCK_RESIST, TOX_RESIST, MENTAL_RESIST, LIGHT_RESIST, DARK_RESIST);

  public static List<Stat> elementalResistances = List.of(HEAT_RESIST, COLD_RESIST, SHOCK_RESIST, TOX_RESIST);
  public static List<Stat> nonResourceStats =
      List.of(
          Stat.MIND,
          Stat.BODY,
          Stat.DEXTERITY,
          Stat.DODGE,
          Stat.CRIT_CHANCE,
          Stat.LETHALITY,
          Stat.HEAT_RESIST,
          Stat.COLD_RESIST,
          Stat.LIGHT_RESIST,
          Stat.DARK_RESIST,
          Stat.SHOCK_RESIST,
          Stat.MENTAL_RESIST,
          Stat.TOX_RESIST);
  public static List<Stat> maxStats = List.of(VITALITY, ENERGY);

  Stat(String translationString, String iconKey, String colorKey) {
    this.translationString = translationString;
    this.iconKey = iconKey;
    this.colorKey = colorKey;
  }

  public String getTranslationString() {
    return translationString;
  }

  public String getIconKey() {
    return iconKey;
  }

  public String getIconString() {
    return "[" + iconKey + "]";
  }

  public String getFullStringReference() {
    return colorKey + this.name() + getIconString() + Color.WHITE.getCodeString();
  }

  public String getColorKey() {
    return colorKey;
  }

  public static Stat getRdmStat() {

    Stat[] stdStats = new Stat[] {MIND, BODY, DEXTERITY, LETHALITY, DODGE};
    Random random = new Random();
    int rndInt = random.nextInt(5);
    return stdStats[rndInt];
  }

  public String getReference() {
    if (maxStats.contains(this)) {
      return this.getColorKey() + "Max" + Color.WHITE.getCodeString() + this.getIconString();
    }
    return this.getIconString();
  }
}
