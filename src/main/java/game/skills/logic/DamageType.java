package game.skills.logic;

public enum DamageType {
  HEAT,
  FROST,
  NORMAL,
  MENTAL,
  LIGHT,
  DARK,
  SHOCK,
  TOXIC;

  public boolean isElemental() {
    return this.equals(HEAT) || this.equals(FROST) || this.equals(TOXIC) || this.equals(SHOCK);
  }
}
