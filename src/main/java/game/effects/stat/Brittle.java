package game.effects.stat;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Brittle extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    if (pl.target.equals(this.hero) && Stat.BODY.equals(pl.stat)) {
      pl.value *= (int) ((int) keyValues.get("BrittlePercentage") / 100);
    }
  }
}
