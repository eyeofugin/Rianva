package game.skills.trees.classes.rogue;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.stat.Brittle;
import game.effects.status.Bleeding;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

public class S_FindTheWeakness extends Skill {
  public void onDamage(ConnectionPayload pl) {
    int chance = Utils.chanceChanges(pl.target, this.hero, (int)keyValues.get("Chance"), this, null, null, pl.depth + 1);
    if (MyMaths.success(chance)) {
      pl.target.addEffect(EffectLibrary.getEffect(Bleeding.class.getName(), 0, 1, null), this.hero);
    }
    if (MyMaths.success(chance)) {
      pl.target.addEffect(EffectLibrary.getEffect(Brittle.class.getName(), 0, 1, null), this.hero);
    }
  }
}
