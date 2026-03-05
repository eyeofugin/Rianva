package game.effects.hero;

import framework.connector.ConnectionPayload;
import game.effects.Effect;
import game.effects.EffectLibrary;
import game.effects.status.Shocked;
import game.entities.Hero;
import game.skills.logic.TargetType;

import java.util.Arrays;
import java.util.List;

public class ThunderbirdForm extends Effect {
  public void targetChange(ConnectionPayload pl) {
    if (pl.skill.hero != null
        && pl.skill.hero.equals(this.hero)
        && (TargetType.SINGLE.equals(pl.skill.getTargetType()))) {
      List<Hero> targets = pl.skill.getTargets();
      this.hero.getEnemies().stream()
          .filter(enemy -> !targets.contains(enemy))
          .filter(enemy -> enemy.hasPermanentEffect(Shocked.class))
          .forEach(targets::add);
      pl.skill.setTargets(targets.toArray(new Hero[0]));
    }
  }

  public void onPerform(ConnectionPayload pl) {
    if (pl.skill != null && this.hero.equals(pl.skill.hero)) {
      this.hero.addEffect(EffectLibrary.getEffect("Flawless", -1, 1, null), this.hero);
    }
  }
}
