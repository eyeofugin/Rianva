package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.status.Blight;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Deadly extends Effect {
  public void onDmg(ConnectionPayload pl) {
    int additionalDmg = pl.dmg * (int) keyValues.get("DmgPercentage") / 100;
    pl.target.damage(additionalDmg, DamageType.DARK, DamageMode.EFFECT, this.origin, null, this, null, 0);
    pl.target.addEffect(EffectLibrary.getEffect(Blight.class.getName(), -1, 2, null), pl.caster);
    this.used = true;
  }
}
