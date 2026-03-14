package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.status.Poisoned;

public class SnakeForm extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    pl.dmg = (int) (pl.dmg * (double) keyValues.get("DmgMult"));
  }

  public void castChange(ConnectionPayload pl) {
    pl.skill.getTargetEffects().add(EffectLibrary.getEffect(Poisoned.class.getName(), 1, -1, null));
  }
}
