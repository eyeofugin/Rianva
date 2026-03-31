package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.status.Frost;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Icy extends Effect {
  public void onDmg(ConnectionPayload pl) {
    int additionalDmg = pl.dmg * (int) keyValues.get("DmgPercentage") / 100;
    pl.target.damage(additionalDmg, DamageType.COLD, DamageMode.EFFECT, this.origin, null, this, null, 0);
    pl.target.addEffect(EffectLibrary.getEffect(Frost.class.getName(), 1, -1, null), pl.caster);
    this.used = true;
  }
}
