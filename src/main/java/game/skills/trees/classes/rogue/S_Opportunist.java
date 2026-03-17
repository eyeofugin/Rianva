package game.skills.trees.classes.rogue;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.hero.Advantage;
import game.effects.hero.Initiative;
import game.skills.Skill;
import utils.Utils;

public class S_Opportunist extends Skill {
  public void onCrit(ConnectionPayload pl) {
    ConnectionPayload.CondEffectImpact impact =
        Utils.condTriggerChanges(this.hero, this, null, null, pl.depth + 1);
    if (ConnectionPayload.CondEffectImpact.ALLOW.equals(impact)
        || this.hero.hasPermanentEffect(Advantage.class)) {
      this.hero.addEffect(
          EffectLibrary.getEffect(Initiative.class.getName(), 0, 0, null), this.hero);
    } else {
      this.hero.addEffect(
          EffectLibrary.getEffect(Advantage.class.getName(), 0, 0, null), this.hero);
    }
  }
}
