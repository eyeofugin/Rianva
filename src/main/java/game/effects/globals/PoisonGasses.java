package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class PoisonGasses extends Effect {

  public void endOfRound(ConnectionPayload pl) {
    pl.arena
        .getAllLivingEntities()
        .forEach(hero -> hero.percentageDamage(10, null, null, this, null, 0));
  }
}
