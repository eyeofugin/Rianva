package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import utils.Utils;

public class Trophy extends Effect {
  public void baseStatChange(ConnectionPayload pl) {
    int changeValue = this.stacks + (int) keyValues.get("Bonus");
    changeValue = Utils.statChangesChanges(this.hero, this.hero, pl.stat, changeValue, null, null, this, pl.depth);
    pl.value += changeValue;
  }
}
