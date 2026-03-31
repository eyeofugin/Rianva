package game.skills.trees.classes.radiance;

import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.effects.status.*;
import game.entities.Hero;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

public class S_PrismRay extends Skill {

  @Override
  public void customTargetEffect(Hero target) {
    int chance = (int)keyValues.get("Chance");
    chance = Utils.chanceChanges(target, this.hero, chance, this, null, null, 1);
    tryEffect(EffectLibrary.getEffect(Bleeding.class.getName(), 0,1,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Blight.class.getName(), 0,1,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Blinded.class.getName(), 0,1,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Burning.class.getName(), 1,0,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Dazed.class.getName(), 0,1,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Frost.class.getName(), 1,0,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Immobile.class.getName(), 0,1,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Poisoned.class.getName(), 1,0,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Shocked.class.getName(), 1,0,null), target, chance);
    tryEffect(EffectLibrary.getEffect(Taunted.class.getName(), 0,1,null), target, chance);
  }
  private void tryEffect(Effect effect, Hero target, int chance) {
    if (MyMaths.success(chance)) {
      target.addEffect(effect, this.hero);
      this.hero.addEffect(effect.copy(), this.hero);
    }
  }
}
