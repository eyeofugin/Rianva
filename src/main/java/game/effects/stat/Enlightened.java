package game.effects.stat;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Enlightened extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    if (pl.target.equals(this.hero) && Stat.MIND.equals(pl.stat)) {
      pl.value += pl.value * (int) keyValues.get("EnlightenedPercentage") / 100;
    }
  }
}
