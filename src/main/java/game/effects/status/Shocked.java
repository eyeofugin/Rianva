package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;

public class Shocked extends Effect {
  public void onDamage(ConnectionPayload pl) {
    if (this.hero.equals(pl.target)) {
      this.addStack();
    }
  }

  @Override
  public void addStacks(int amount) {
    this.stacks += amount;
    if (this.stacks == (int) keyValues.get("ShockedMaxStack")) {
      this.hero.addEffect(EffectLibrary.getEffect("Stunned", -1, -1, null), this.origin);
      this.stacks = 0;
    }
  }
}
