package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class Cover extends Effect {
  public void statChange(ConnectionPayload pl) {
    if ( Stat.DODGE.equals(pl.stat) && pl.target.getPosition() == this.position) {
      pl.value += (int) keyValues.get("DodgeBonus");
    }
  }

  public void onPerform(ConnectionPayload pl) {
    if (pl.skill.hero.getPosition() == this.position) {
      this.turns = 0;
    }
  }
}
