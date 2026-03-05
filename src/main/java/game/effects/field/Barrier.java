package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.DamageMode;
import game.skills.logic.Stat;

public class Barrier extends Effect {
  public void dmgChange(ConnectionPayload pl) {
    if (pl.target.getPosition() == this.position
        && pl.skill != null
        && DamageMode.PHYSICAL.equals(pl.skill.getDamageMode())) {
      pl.dmg = (int) (pl.dmg * (double) keyValues.get("DmgChange"));
    }
  }
}
