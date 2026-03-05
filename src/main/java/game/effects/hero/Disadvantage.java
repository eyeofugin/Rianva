package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Disadvantage extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    if (pl.caster != null && pl.caster.equals(this.hero) && pl.skill != null) {
      pl.dmg = (int) (pl.dmg * (double) keyValues.get("Change"));
      this.used = true;
    }
  }
}
