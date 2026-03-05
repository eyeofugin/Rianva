package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.entities.Hero;

public class Thicket extends Effect {
  public void endOfTurn(ConnectionPayload pl) {
    Hero hero = this.arena.getAtPosition(this.position);
    if (hero != null) {
      hero.percentageDamage(
          (double) keyValues.get("DmgPercentage"), this.origin, null, this, null, 0);
    }
  }
  public void canPerform(ConnectionPayload pl) {
    if (pl.skill.hero.getPosition() == this.position) {
      pl.failure = pl.skill.isMoveTo();
    }
  }
}
