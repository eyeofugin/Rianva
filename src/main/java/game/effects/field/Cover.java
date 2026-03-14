package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Cover extends Effect {
  public void statChange(ConnectionPayload pl) {
    pl.value += (int) keyValues.get("DodgeBonus");
  }

  public void onPerform(ConnectionPayload pl) {
    this.turns = 0;
  }
}
