package game.skills.trees.classes.trickster;

import framework.Logger;
import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.entities.Hero;
import game.skills.Skill;

public class S_PocketHex extends Skill {

    @Override
    public void customTargetEffect(Hero target) {
        Logger.logLn("S_PocketHex.customTargetEffect()");
        Effect effect = EffectLibrary.getRandomStatusDebuff();
        target.addEffect(effect, this.hero);
    }
}
