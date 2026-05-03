package game.skills.trees.classes.radiance;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.effects.hero.Advantage;
import game.skills.Skill;

public class S_Halo extends Skill {
    public void statChange(ConnectionPayload pl) {
        Logger.logLn("S_Halo.statChange()");
      if (this.hero.equals(pl.target)) {
        pl.value += (int)keyValues.get("Self");
      } else if (this.hero.hasPermanentEffect(Advantage.class)) {
        pl.value += (int)keyValues.get("Others");
      }
    }
}
