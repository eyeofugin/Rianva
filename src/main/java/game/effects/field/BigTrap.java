package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.status.Stunned;
import game.entities.Hero;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;

public class BigTrap extends Effect {
  public void onEnter(ConnectionPayload pl) {
    this.used = true;
    Hero hero = this.arena.getAtPosition(position);
    hero.percentageDamage(
        (int) keyValues.get("DmgPercentage"),
        DamageType.NORMAL,
        DamageMode.EFFECT,
        this.origin,
        null,
        this,
        null,
        0);
    hero.addEffect(EffectLibrary.getEffect(Stunned.class.getName(), 0, -1, null), this.origin);
  }
}
