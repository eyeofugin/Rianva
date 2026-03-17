package game.skills.trees.classes.radiance;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.hero.Advantage;
import game.effects.status.Blinded;
import game.skills.Skill;
import game.skills.logic.Condition;

public class S_LightBarrier extends Skill {
  public void castChange(ConnectionPayload pl) {
    if (this.equals(pl.skill) && this.hero.hasPermanentEffect(Advantage.class)) {
      this.effects.forEach(e->e.turns += 2);
    }
  }
}
