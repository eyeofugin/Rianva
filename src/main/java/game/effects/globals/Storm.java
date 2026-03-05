package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class Storm extends Effect {

  public void dmgChanges(ConnectionPayload pl) {
    if (pl.skill.getDamageType().isElemental()) {
      double newDmg = pl.dmg * 1.25;
      pl.dmg = (int) newDmg;
    }
  }
}
