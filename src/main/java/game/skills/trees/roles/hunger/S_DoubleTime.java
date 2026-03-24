package game.skills.trees.roles.hunger;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.hero.Marked;
import game.skills.Skill;
import utils.MyMaths;

public class S_DoubleTime extends Skill {
  public void castChange(ConnectionPayload pl) {
    if (this.hero.getEffects().stream()
        .anyMatch(
            e ->
                e.subTypes.contains(Effect.SubType.ATK_ENHANCE)
                    || e.subTypes.contains(Effect.SubType.BONUS))) {
      pl.skill.setCountsAsHits(pl.skill.getCountsAsHits() + 1);
    }
  }
}
