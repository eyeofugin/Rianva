package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Blight extends Effect {
  public void onDamage(ConnectionPayload pl) {
    if (this.hero.equals(pl.target)) {
      this.hero.percentageDamage(
          (int) keyValues.get("BlightDmgPercentage"), this.origin, null, this, null, 0);
    }
  }
}
