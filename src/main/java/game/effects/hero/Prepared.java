package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Prepared extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    if (pl.target != null && pl.target.equals(this.hero) && pl.skill != null) {
      pl.dmg = (int) (pl.dmg * (double) keyValues.get("Change"));
      this.used = true;
    }
  }
}
