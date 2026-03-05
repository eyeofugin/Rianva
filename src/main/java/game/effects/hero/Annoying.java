package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class Annoying extends Effect {
  public void onDmg(ConnectionPayload pl) {
    if (pl.caster != null && pl.caster.equals(this.hero)) {
      pl.target.addEffect(EffectLibrary.getEffect("Taunted", -1, 2, null), pl.caster);
      this.used = true;
    }
  }
}
