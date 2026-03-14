package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.stat.Enlightened;
import game.skills.logic.Stat;
import game.skills.logic.TargetType;

public class DragonForm extends Effect {
  public void dmgChangeMult(ConnectionPayload pl) {
    pl.dmg = (int) (pl.dmg * (double) keyValues.get("AoeDmgMult"));
  }

  public void castChange(ConnectionPayload pl) {
    pl.skill.setManaCost(pl.skill.getManaCost() + (int) keyValues.get("CostIncrease"));
  }

  public void onPerform(ConnectionPayload pl) {
    this.hero.addEffect(EffectLibrary.getEffect(Enlightened.class.getName(), -1, 1, null), this.hero);
  }
}
