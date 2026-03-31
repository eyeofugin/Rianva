package game.skills.trees.classes.trickster;

import game.effects.Effect;
import game.libraries.EffectLibrary;
import game.entities.Hero;
import game.skills.Skill;

public class S_PocketHex extends Skill {

    @Override
    public void customTargetEffect(Hero target) {
        Effect effect = EffectLibrary.getRandomStatusDebuff();
        target.addEffect(effect, this.hero);
    }
}
