package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class Icy extends Effect {
  public void onDmg(ConnectionPayload pl) {
    if (pl.caster != null && pl.caster.equals(this.hero)) {
      int additionalDmg = pl.dmg * (int) keyValues.get("DmgPercentage") / 100;
      pl.target.damage(additionalDmg, this.origin, null, this, null, 0);
      pl.target.addEffect(EffectLibrary.getEffect("Frost", 1, -1, null), pl.caster);
      this.used = true;
    }
  }
}
