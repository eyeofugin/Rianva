package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class PoisonGasses extends Effect {

  public void endOfRound(ConnectionPayload pl) {
    pl.arena
        .getAllLivingEntities()
        .forEach(
            hero ->
                hero.percentageDamage(
                    10, DamageType.TOXIC, DamageMode.EFFECT, null, null, this, null, 0));
  }
}
