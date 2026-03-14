package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class DoubleStrike extends Effect {
  public void castChange(ConnectionPayload pl) {
    pl.skill.setCountsAsHits(pl.skill.getCountsAsHits() * 2);
  }
}
