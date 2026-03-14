package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import game.skills.trees.races.S_Unlife;

public class Blight extends Effect {
  public void onDamage(ConnectionPayload pl) {
    if (this.hero.hasSkill(S_Unlife.class)) {
      this.hero.percentageHeal((int) keyValues.get("BlightDmgPercentage"), this.origin, null, this, null, false);
    } else {
      this.hero.percentageDamage(
              (int) keyValues.get("BlightDmgPercentage"), DamageType.DARK, DamageMode.EFFECT, this.origin, null, this, null, 0);
    }
  }
}
