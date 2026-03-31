package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Eclipse extends Effect {

  public void castChange(ConnectionPayload pl) {
    if (pl.skill.getEffects() != null) {
      for (Effect effect : pl.skill.getEffects()) {
        increase(effect);
      }
    }
    if (pl.skill.getCasterEffects() != null) {
      for (Effect effect : pl.skill.getCasterEffects()) {
        increase(effect);
      }
    }
  }

  private void increase(Effect effect) {
    if (effect.turns > 0) {
      effect.turns++;
    }
  }
}
