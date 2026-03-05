package game.effects.stat;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Shaken extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    if (pl.target.equals(this.hero) && Stat.DEXTERITY.equals(pl.stat)) {
      pl.value *= (int) ((int) keyValues.get("ShakenPercentage") / 100);
    }
  }
}
