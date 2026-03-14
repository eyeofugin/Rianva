package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;
import utils.Utils;

public class Blinded extends Effect {
  public void baseStatChange(ConnectionPayload pl) {
    int changeValue = Utils.statChangesChanges(this.hero, this.hero, pl.stat, (int) keyValues.get("AccuracyMalus"), null, null, this, pl.depth);
    pl.value += changeValue;
  }
}
