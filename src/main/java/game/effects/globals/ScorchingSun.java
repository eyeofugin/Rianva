package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class ScorchingSun extends Effect {

  public void effectDmgChanges(ConnectionPayload pl) {
    pl.dmg *= 2;
  }

  public void effectFailure(ConnectionPayload pl) {
    pl.failure = true;
  }
}
