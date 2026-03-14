package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.status.Blinded;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Glorious extends Effect {
  public void onDmg(ConnectionPayload pl) {
    int additionalDmg = pl.dmg * (int) keyValues.get("DmgPercentage") / 100;
    pl.target.damage(additionalDmg, DamageType.LIGHT, DamageMode.EFFECT, this.origin, null, this, null, 0);
    pl.target.addEffect(EffectLibrary.getEffect(Blinded.class.getName(), -1, 2, null), pl.caster);
    this.used = true;
  }
}
