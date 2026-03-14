package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.status.Taunted;

public class Annoying extends Effect {
  public void onDmg(ConnectionPayload pl) {
    pl.target.addEffect(EffectLibrary.getEffect(Taunted.class.getName(), -1, 2, null), pl.caster);
    this.used = true;
  }
}
