package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class Lifesteal extends Effect {
  public void dmgTrigger(ConnectionPayload pl) {
    if (pl.caster.equals(this.hero)) {
      this.hero.heal(getLife(pl.dmg), this.hero, null, this, null, false);
    }
  }

  private int getLife(int dmgDone) {
    return dmgDone * (Integer) this.keyValues.get("LifePercentage") / 100;
  }
}
