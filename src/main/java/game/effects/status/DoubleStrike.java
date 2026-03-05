package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class DoubleStrike extends Effect {
  public void castChange(ConnectionPayload pl) {
    if (pl.skill.hero.equals(this.hero)) {
      pl.skill.setCountsAsHits(pl.skill.getCountsAsHits() * 2);
    }
  }
}
