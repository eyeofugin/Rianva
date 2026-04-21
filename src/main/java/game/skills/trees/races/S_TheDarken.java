package game.skills.trees.races;

import framework.connector.ConnectionPayload;
import game.effects.globals.Darkness;
import game.skills.Skill;
import utils.Utils;

public class S_TheDarken extends Skill {
  public void dmgChanges(ConnectionPayload pl) {
    ConnectionPayload.CondEffectImpact impact =
        Utils.condTriggerChanges(this.hero, this, null, null, ++pl.depth);

    if (ConnectionPayload.CondEffectImpact.ALLOW.equals(impact)
        || (ConnectionPayload.CondEffectImpact.IGNORE.equals(impact)
            && this.hero.arena.hasGlobalEffect(Darkness.class))) {
      int change = (int) ((double) keyValues.get("Mult") * pl.dmg);
      pl.dmg += change;
    }
  }
}
