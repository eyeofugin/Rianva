package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.entities.Hero;
import game.skills.logic.Stat;

public class Dueling extends Effect {

  private Hero target;

  public void dmgChangesMult(ConnectionPayload pl) {
    if (this.hero.equals(pl.caster) && this.target != null && this.target.equals(pl.target)) {
      pl.dmg = pl.dmg + (int)(pl.dmg * (double) keyValues.get("MultPerStack") * this.stacks);
    }
  }

  public void dmgTrigger(ConnectionPayload pl) {
    if (this.hero.equals(pl.skill.hero)) {
      if (this.target == null || !this.target.equals(pl.target)) {
        this.stacks = 1;
      } else {
        this.stacks++;
      }
    }
  }
}
