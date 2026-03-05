package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;
import utils.MyMaths;

public class PlanA extends Effect {
  public void statChangeMult(ConnectionPayload pl) {
    if (pl.target != null && pl.target.equals(this.hero)) {
      if (Stat.BODY.equals(pl.stat)
          || Stat.MIND.equals(pl.stat)
          || Stat.DEXTERITY.equals(pl.stat)) {
        pl.value = (int) (pl.value * (double) keyValues.get("Change"));
      }
    }
  }
}
