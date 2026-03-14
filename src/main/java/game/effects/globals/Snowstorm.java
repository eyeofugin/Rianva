package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Snowstorm extends Effect {

  public void accChange(ConnectionPayload pl) {
    pl.skill.setAccuracy(pl.skill.getAccuracy() - 5);
  }

  public void effectFailure(ConnectionPayload pl) {
    pl.failure = true;
  }

  public void endOfRound(ConnectionPayload pl) {
    pl.arena
        .getAllLivingEntities()
        .forEach(
            hero ->
                hero.percentageDamage(
                    5, DamageType.COLD, DamageMode.EFFECT, null, null, this, null, 0));
  }
}
