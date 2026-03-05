package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class Darkness extends Effect {

  public void accChange(ConnectionPayload pl) {
    pl.skill.setAccuracy(pl.skill.getAccuracy() - 15);
  }
}
