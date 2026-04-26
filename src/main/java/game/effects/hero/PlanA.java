package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import utils.Utils;

public class PlanA extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    int changeValue = (int) (pl.value * (double) keyValues.get("Change"));
    changeValue = Utils.statChangesChanges(this.hero, this.hero, pl.stat, changeValue, null, null, this);
    pl.value += changeValue;
  }
}
