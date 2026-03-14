package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class GloriousDay extends Effect {

  public void healChanges(ConnectionPayload pl) {
    double newHeal = pl.heal *  (double)keyValues.get("Heal");
    pl.heal = (int) newHeal;
  }

  public void shieldChanges(ConnectionPayload pl) {
    double newShield = pl.shield * (double)keyValues.get("Shield");
    pl.shield = (int) newShield;
  }
}
