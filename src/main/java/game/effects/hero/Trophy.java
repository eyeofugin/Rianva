package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Trophy extends Effect {
  public void baseStatChange(ConnectionPayload pl) {
    if (pl.target != null && pl.target.equals(this.hero)) {
      if (Stat.BODY.equals(pl.stat)
          || Stat.MIND.equals(pl.stat)
          || Stat.DEXTERITY.equals(pl.stat)
          || Stat.DODGE.equals(pl.stat)) {
        pl.value += this.stacks + (int) keyValues.get("Bonus");
      }
    }
  }
}
