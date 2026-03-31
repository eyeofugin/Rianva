package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.stat.Brittle;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import game.skills.trees.races.S_BornInFlames;

public class Burning extends Effect {

  public void endOfTurn(ConnectionPayload pl) {
    if (this.hero.hasSkill(S_BornInFlames.class)) {
      this.hero.percentageHeal(
              (int) keyValues.get("BurningEotDmgPercentagePerStack") * this.stacks,
              this.origin,
              null,
              this,
              null,
              false);
    } else {
      this.hero.percentageDamage(
              (int) keyValues.get("BurningEotDmgPercentagePerStack") * this.stacks,
              DamageType.HEAT,
              DamageMode.EFFECT,
              this.origin,
              null,
              this,
              null,
              0);
    }
    this.addStack();
  }

  @Override
  public void addStacks(int amount) {
    this.stacks += amount;
    if (this.stacks == (int) this.keyValues.get("BurningStackMax")) {
      this.hero.percentageDamage(
          (int) keyValues.get("BurningFinalDmgPercentage"),
          DamageType.HEAT,
          DamageMode.EFFECT,
          this.origin,
          null,
          this,
          null,
          0);
      this.hero.addEffect(
          EffectLibrary.getEffect(Brittle.class.getName(), 0, (int) keyValues.get("BrittleTurns"), null),
          this.origin);
      this.stacks = 0;
    }
  }
}
