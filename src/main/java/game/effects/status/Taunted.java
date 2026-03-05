package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.SkillTag;

public class Taunted extends Effect {
  public void canPerformCheck(ConnectionPayload pl) {
    pl.failure = pl.failure || performFail(pl);
  }

  private boolean performFail(ConnectionPayload pl) {
    return this.hero.equals(pl.caster) && !pl.skill.tags.contains(SkillTag.PRIMARY);
  }
}
