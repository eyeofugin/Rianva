package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class Burning extends Effect {

  public void endOfTurn(ConnectionPayload pl) {
    this.hero.percentageDamage(
        (int) keyValues.get("BurningEotDmgPercentagePerStack") * this.stacks,
        this.origin,
        null,
        this,
        null,
        0);
    this.addStack();
  }

  @Override
  public void addStacks(int amount) {
    this.stacks += amount;
    if (this.stacks == (int) this.keyValues.get("BurningStackMax")) {
      this.hero.percentageDamage(
          (int) keyValues.get("BurningFinalDmgPercentage"), this.origin, null, this, null, 0);
      this.hero.addEffect(
          EffectLibrary.getEffect("Brittle", 0, (int) keyValues.get("BrittleTurns"), null),
          this.origin);
      this.stacks = 0;
    }
  }
}
