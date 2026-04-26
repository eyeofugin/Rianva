package game.effects.stat;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;
import utils.Utils;

public class Shaken extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    int changeValue = (int) (pl.value * (double) keyValues.get("Percentage"));
    changeValue = Utils.statChangesChanges(this.hero, this.hero, pl.stat, changeValue, null, null, this);
    pl.value += changeValue;
  }
}
