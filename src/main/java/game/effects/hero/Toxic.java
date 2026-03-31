package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.status.Poisoned;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Toxic extends Effect {
  public void onDmg(ConnectionPayload pl) {
    int additionalToxDmg = pl.dmg * (int) keyValues.get("DmgPercentage") / 100;
    pl.target.damage(
        additionalToxDmg, DamageType.TOXIC, DamageMode.EFFECT, this.origin, null, this, null, 0);
    pl.target.addEffect(EffectLibrary.getEffect(Poisoned.class.getName(), 1, -1, null), pl.caster);
    this.used = true;
  }
}
