package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.DamageMode;
import game.skills.logic.Stat;

public class Barrier extends Effect {
  public void dmgChange(ConnectionPayload pl) {
    pl.dmg = (int) (pl.dmg * (double) keyValues.get("DmgChange"));
  }
}
