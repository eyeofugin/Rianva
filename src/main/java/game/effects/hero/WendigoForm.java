package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;

public class WendigoForm extends Effect {
  public void castChange(ConnectionPayload pl) {
    if (pl.skill != null
        && this.hero.equals(pl.skill.hero)) {
      pl.skill.setManaCost((int)(pl.skill.getManaCost() * (double) keyValues.get("ManaCostChange")));
    }
  }

  public void onPerform(ConnectionPayload pl) {
    if (pl.skill.hero.equals(this.hero)) {
      this.hero.arena.extraTurn(this.hero);
    }
  }
}
