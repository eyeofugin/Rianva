package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.entities.Hero;

public class RecoveryField extends Effect {
  public void endOfRound(ConnectionPayload pl) {
    Hero hero = this.arena.getAtPosition(this.position);
    hero.percentageHeal((int) keyValues.get("HealPercentage"), this.origin, null, this, null, false);
  }
}
