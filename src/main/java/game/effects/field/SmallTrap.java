package game.effects.field;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.status.Bleeding;
import game.entities.Hero;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import game.skills.logic.Stat;

public class SmallTrap extends Effect {
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
    hero.addEffect(EffectLibrary.getEffect(Bleeding.class.getName(), 0, 1, null), this.origin);
  }
}
