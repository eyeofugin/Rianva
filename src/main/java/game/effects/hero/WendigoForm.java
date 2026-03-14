package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class WendigoForm extends Effect {
  public void castChange(ConnectionPayload pl) {
    pl.skill.setManaCost((int)(pl.skill.getManaCost() * (double) keyValues.get("ManaCostChange")));
  }

  public void onPerform(ConnectionPayload pl) {
    this.hero.arena.extraTurn(this.hero);

  }
}
