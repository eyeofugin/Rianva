package game.effects.hero;

import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import game.effects.Effect;
import utils.Utils;

public class Marked extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    pl.dmg = (int) (pl.dmg * (double) keyValues.get("Change"));
  }
  public void dmgTrigger(ConnectionPayload pl) {
    this.used = true;
    Utils.onMark(this.hero, pl.depth);
  }
}
