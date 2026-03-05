package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.DamageMode;

public class Reflection extends Effect {
  public void dmgChange(ConnectionPayload pl) {
    if (pl.target.getPosition() == this.position
        && pl.skill != null
        && DamageMode.MAGICAL.equals(pl.skill.getDamageMode())) {
      pl.dmg = (int) (pl.dmg * (double) keyValues.get("DmgChange"));
    }
  }
}
