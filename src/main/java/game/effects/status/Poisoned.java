package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Poisoned extends Effect {
  public void endOfTurn(ConnectionPayload pl) {
    this.hero.percentageDamage(
        (int) keyValues.get("PoisonEotDmgPercentagePerStack") * this.stacks,
        DamageType.TOXIC,
        DamageMode.EFFECT,
        this.origin,
        null,
        this,
        null,
        0);
    this.addStack();
  }
}
