package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Marked extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    if (this.hero.equals(pl.target) && pl.skill != null) {
      pl.dmg = (int) (pl.dmg * (double) keyValues.get("Change"));
      this.used = true;
    }
  }
}
