package game.skills.logic;

public enum DamageType {
  HEAT,
  COLD,
  NORMAL,
  MENTAL,
  LIGHT,
  DARK,
  SHOCK,
  TOXIC,
  TRUE;

  public boolean isElemental() {
    return this.equals(HEAT) || this.equals(COLD) || this.equals(TOXIC) || this.equals(SHOCK);
  }
}
