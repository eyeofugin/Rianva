package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.entities.Hero;

public class BigTrap extends Effect {
  public void onEnter(ConnectionPayload pl) {
    this.used = true;
    Hero hero = this.arena.getAtPosition(position);
    hero.percentageDamage((int)keyValues.get("DmgPercentage"), this.origin, null, this, null, 0);
    hero.addEffect(EffectLibrary.getEffect("Stunned", 0, -1, null), this.origin);
  }
}