package game.effects.status;

import framework.connector.ConnectionPayload;
import framework.connector.Connector;
import game.effects.Effect;

public class Leech extends Effect {
  public void dmgTrigger(ConnectionPayload pl) {
    if (pl.caster.equals(this.hero)) {
      this.hero.energy(getEnergy(pl.dmg), this.hero, null, this, null, false);
    }
  }

  private int getEnergy(int dmgDone) {
    return dmgDone * (Integer) this.keyValues.get("EnergyPercentage") / 100;
  }
}
