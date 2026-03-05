package game.effects.globals;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.skills.logic.Stat;

public class AetherWinds extends Effect {

  public void endOfRound(ConnectionPayload pl) {
    pl.arena.getAllLivingEntities().stream()
        .filter(
            e -> e.getSecondaryResource() != null && e.getSecondaryResource().equals(Stat.ENERGY))
        .forEach(e -> e.energy(1, null, null, this, null, false));
  }
}
