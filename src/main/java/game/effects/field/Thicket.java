package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.entities.Hero;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Thicket extends Effect {
  public void endOfTurn(ConnectionPayload pl) {
    Hero hero = this.arena.getAtPosition(this.position);
    if (hero != null) {
      hero.percentageDamage(
          (double) keyValues.get("DmgPercentage"),
          DamageType.TOXIC,
          DamageMode.EFFECT,
          this.origin,
          null,
          this,
          null,
          0);
    }
  }

  public void canPerform(ConnectionPayload pl) {
    pl.failure = pl.skill.isMoveTo();
  }
}
