package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.stat.Enlightened;

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
