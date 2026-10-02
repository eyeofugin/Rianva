package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.legacy.logic.DamageMode;
import game.skills.legacy.logic.DamageType;
import game.skills.legacy.trees.races.S_Unlife;

public class Blight extends Effect {
  public void onDamage(ConnectionPayload pl) {
    if (!used) {
      used = true;
      if (this.hero.hasSkill(S_Unlife.class)) {
        this.hero.percentageHeal((int) keyValues.get("BlightDmgPercentage"), this.origin, null, this, null, false);
      } else {
        this.hero.percentageDamage(
                (int) keyValues.get("BlightDmgPercentage"), DamageType.DARK, DamageMode.EFFECT, this.origin, null, this, null, 0);
      }
    }
  }
}
