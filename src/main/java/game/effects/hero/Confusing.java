package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.status.Dazed;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Confusing extends Effect {
  public void onDmg(ConnectionPayload pl) {
    int additionalMentalDmg = pl.dmg * (int) keyValues.get("DmgPercentage") / 100;
    pl.target.damage(additionalMentalDmg, DamageType.MENTAL, DamageMode.EFFECT, this.origin, null, this, null, 0);
    pl.target.addEffect(EffectLibrary.getEffect(Dazed.class.getName(), -1, 2, null), pl.caster);
    this.used = true;
  }
}
