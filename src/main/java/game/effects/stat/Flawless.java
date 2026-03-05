package game.effects.stat;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Flawless extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    if (pl.target.equals(this.hero) && Stat.DEXTERITY.equals(pl.stat)) {
      pl.value += pl.value * (int) keyValues.get("FlawlessPercentage") / 100;
    }
  }
}
