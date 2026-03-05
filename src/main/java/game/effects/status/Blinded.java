package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Blinded extends Effect {
  public void baseStatChange(ConnectionPayload pl) {
    if (pl.target.equals(this.hero) && Stat.ACCURACY.equals(pl.stat)) {
      pl.value -= (int) keyValues.get("AccuracyMalus");
    }
  }
}
