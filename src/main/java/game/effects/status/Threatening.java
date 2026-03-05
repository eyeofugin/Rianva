package game.effects.status;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.TargetType;

public class Threatening extends Effect {

  public void canPerformCheck(ConnectionPayload pl) {
    pl.failure = pl.failure || performFail(pl);
  }

  private boolean performFail(ConnectionPayload pl) {
    if (this.hero.isAlly(pl.caster)) {
      return false;
    }
    return (TargetType.SINGLE.equals(pl.skill.getTargetType())
            || TargetType.SINGLE_OTHER.equals(pl.skill.getTargetType()))
        && pl.targetPositions.length == 1
        && pl.targetPositions[0] != this.hero.getPosition();
  }
}
