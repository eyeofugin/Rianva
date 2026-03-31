package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.status.Stunned;

public class Stunning extends Effect {

  public void onDmg(ConnectionPayload pl) {
    pl.target.addEffect(EffectLibrary.getEffect(Stunned.class.getName(), -1, -1, null), pl.caster);
    this.used = true;
  }
}
