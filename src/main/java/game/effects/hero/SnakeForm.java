package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class SnakeForm extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    if (this.hero.equals(pl.caster)
        && pl.target != null
        && pl.target.hasStatusDebuff()) {
      pl.dmg = (int) (pl.dmg * (double) keyValues.get("DmgMult"));
    }
  }

  public void castChange(ConnectionPayload pl) {
    if (pl.skill != null
        && this.hero.equals(pl.skill.hero)) {
      pl.skill.getTargetEffects().add(EffectLibrary.getEffect("Poisoned", 1, -1, null));
    }
  }
}
