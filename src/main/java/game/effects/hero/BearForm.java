package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.skills.logic.Stat;

public class BearForm extends Effect {
  public void statChangeBase(ConnectionPayload pl) {
    if (Stat.elementalResistances.contains(pl.stat) && this.hero.equals(pl.target)) {
      pl.value += this.hero.getLevel() * (int) keyValues.get("AdaptiveResist");
    }
  }

  public void onPerform(ConnectionPayload pl) {
    if (pl.skill.hero.equals(this.hero)) {
      this.hero.shield(
          this.hero.getLevel() * (int) keyValues.get("Shield"), this.hero, null, this, null);
    }
  }
}
