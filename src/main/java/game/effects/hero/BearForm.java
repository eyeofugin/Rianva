package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;
import utils.Utils;

public class BearForm extends Effect {
  public void statChangeBase(ConnectionPayload pl) {
    int changeValue = this.hero.getLevel() * (int) keyValues.get("AdaptiveResist");
    changeValue = Utils.statChangesChanges(this.hero, this.hero, pl.stat, changeValue, null, null, this);
    pl.value += changeValue;
  }

  public void onPerform(ConnectionPayload pl) {
    this.hero.shield(
        this.hero.getLevel() * (int) keyValues.get("Shield"), this.hero, null, this, null);
  }
}
