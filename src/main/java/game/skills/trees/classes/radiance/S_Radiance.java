package game.skills.trees.classes.radiance;

import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.hero.Advantage;
import game.effects.status.Blinded;
import game.skills.Skill;
import game.skills.logic.Condition;

public class S_Radiance extends Skill {
  public void castChange(ConnectionPayload pl) {
    pl.skill
        .getEffects()
        .add(
            EffectLibrary.getEffect(
                Blinded.class.getName(), 0, 1, new Condition().setSuccessChance(20)));
  }

  public void onMiss(ConnectionPayload pl) {
    pl.target.addEffect(EffectLibrary.getEffect(Advantage.class.getName(), 0, 1, null), this.hero);
  }
}
