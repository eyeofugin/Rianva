package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;

public class Shocked extends Effect {
  public void onDamage(ConnectionPayload pl) {
    this.addStack();
  }

  @Override
  public void addStacks(int amount) {
    this.stacks += amount;
    if (this.stacks == (int) keyValues.get("ShockedMaxStack")) {
      this.hero.addEffect(EffectLibrary.getEffect(Stunned.class.getName(), -1, -1, null), this.origin);
      this.stacks = 0;
    }
  }
}
