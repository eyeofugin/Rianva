package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class BadTerrain extends Effect {
  public void dmgChange(ConnectionPayload pl) {
    if (pl.target.getPosition() == this.position) {
      pl.dmg = (int)(pl.dmg * (double) keyValues.get("DmgChange"));
    }
  }
}
