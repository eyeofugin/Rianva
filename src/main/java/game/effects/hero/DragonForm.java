package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

public class DragonForm extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    if (pl.caster != null
        && pl.caster.equals(this.hero)
        && pl.skill != null
        && (TargetType.ALL_TARGETS.equals(pl.skill.getTargetType())
            || TargetType.ALL.equals(pl.skill.getTargetType()))) {
      pl.dmg = (int) (pl.dmg * (double) keyValues.get("AoeDmgMult"));
    }
  }

  public void castChange(ConnectionPayload pl) {
    if (pl.caster != null
        && pl.caster.equals(this.hero)
        && pl.skill != null
        && pl.skill.getManaCost() > 0) {
      pl.skill.setManaCost(pl.skill.getManaCost() + (int) keyValues.get("CostIncrease"));
    }
  }

  public void onPerform(ConnectionPayload pl) {
    if (pl.skill != null && this.hero.equals(pl.skill.hero)) {
      this.hero.addEffect(EffectLibrary.getEffect("Enlightened", -1, 1, null), this.hero);
    }
  }
}
