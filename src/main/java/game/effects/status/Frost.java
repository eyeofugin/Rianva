package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.stat.Shaken;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class Frost extends Effect {

  public void endOfTurn(ConnectionPayload pl) {
    this.hero.percentageDamage(
        (int) keyValues.get("FrostEotDmgPercentage"), DamageType.COLD, DamageMode.EFFECT, this.origin, null, this, null, 0);
    addStack();
  }

  @Override
  public void addStacks(int amount) {
    this.stacks += amount;
    if (this.stacks == (int) this.keyValues.get("FrostStackMax")) {
      this.hero.percentageDamage(
          (int) keyValues.get("FrostFinalDmgPercentage"),DamageType.COLD, DamageMode.EFFECT, this.origin, null, this, null, 0);
      this.hero.addEffect(
          EffectLibrary.getEffect(Shaken.class.getName(), -1, (int) keyValues.get("ShakenTurns"), null),
          this.origin);
      this.stacks = 0;
    }
  }
}
