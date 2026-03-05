package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectDTO;
import game.effects.EffectLibrary;

public class Snowstorm extends Effect {

  public void accChange(ConnectionPayload pl) {
    pl.skill.setAccuracy(pl.skill.getAccuracy() - 5);
  }

  public void effectFailure(ConnectionPayload pl) {
    if ("Burn".equals(pl.effect.name)) {
      pl.failure = true;
    }
  }

  public void endOfRound(ConnectionPayload pl) {
    pl.arena
        .getAllLivingEntities()
        .forEach(hero -> hero.percentageDamage(5, null, null, this, null, 0));
  }
}
