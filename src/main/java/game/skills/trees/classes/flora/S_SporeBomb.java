package game.skills.trees.classes.flora;

import game.effects.EffectLibrary;
import game.effects.globals.PoisonGasses;
import game.skills.Skill;
import utils.MyMaths;
import utils.Utils;

public class S_SporeBomb extends Skill {

    @Override
    public void oncePerActivationEffect() {
        int chance = (int) keyValues.get("Chance");
        chance = Utils.chanceChanges(null, this.hero, chance, this, null, null, 1);
        if (MyMaths.success(chance)) {
            this.hero.arena.globalEffect = EffectLibrary.getEffect(PoisonGasses.class.getName(), 0, 5, null);
        }
    }
}
