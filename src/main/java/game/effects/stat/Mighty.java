package game.effects.stat;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Mighty extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    if (pl.target.equals(this.hero) && Stat.BODY.equals(pl.stat)) {
      pl.value += pl.value * (int) keyValues.get("MightyPercentage") / 100;
    }
  }
}
