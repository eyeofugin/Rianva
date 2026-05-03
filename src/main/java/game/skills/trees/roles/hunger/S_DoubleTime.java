package game.skills.trees.roles.hunger;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.Skill;

public class S_DoubleTime extends Skill {
  public void castChange(ConnectionPayload pl) {
    Logger.logLn("S_DoubleTime.castChange()");
    if (this.hero.getEffects().stream()
        .anyMatch(
            e ->
                e.subTypes.contains(Effect.SubType.ATK_ENHANCE)
                    || e.subTypes.contains(Effect.SubType.BONUS))) {
      pl.skill.setCountsAsHits(pl.skill.getCountsAsHits() + 1);
    }
  }
}
