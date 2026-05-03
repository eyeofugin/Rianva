package game.skills.trees.classes.radiance;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.effects.hero.Advantage;
import game.skills.Skill;

public class S_LightReflection extends Skill {
  public void castChange(ConnectionPayload pl) {
    Logger.logLn("S_LightReflection.castChange()");
    if (this.equals(pl.skill) && this.hero.hasPermanentEffect(Advantage.class)) {
      this.effects.forEach(e->e.turns += 2);
    }
  }
}
