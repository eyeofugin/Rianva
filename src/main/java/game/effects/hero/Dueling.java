package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.entities.Hero;

public class Dueling extends Effect {

  private Hero duelTarget;
  public void dmgChangesMult(ConnectionPayload pl) {
    if (this.hero.equals(pl.caster) && this.duelTarget != null && this.duelTarget.equals(pl.target)) {
      pl.dmg = pl.dmg + (int)(pl.dmg * (double) keyValues.get("MultPerStack") * this.stacks);
    }
  }

  public void dmgTrigger(ConnectionPayload pl) {
    if (this.duelTarget == null || !this.duelTarget.equals(pl.target)) {
      this.stacks = 1;
      this.duelTarget = pl.target;
    } else {
      this.stacks++;
    }
  }
}
