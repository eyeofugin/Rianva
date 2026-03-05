package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Poisoned extends Effect {
  public void endOfTurn(ConnectionPayload pl) {
    this.hero.percentageDamage(
        (int) keyValues.get("PoisonEotDmgPercentagePerStack") * this.stacks,
        this.origin,
        null,
        this,
        null,
        0);
    this.addStack();
  }
}
