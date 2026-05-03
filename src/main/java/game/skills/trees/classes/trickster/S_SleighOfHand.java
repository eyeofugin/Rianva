package game.skills.trees.classes.trickster;

import framework.Logger;
import game.effects.Effect;
import game.entities.Hero;
import game.skills.Skill;
import utils.CollectionUtils;

public class S_SleighOfHand extends Skill {
    @Override
    public void customTargetEffect(Hero target) {
        Logger.logLn("S_SleighOfHand.customTargetEffect()");
        Effect debuff = CollectionUtils.getRandom(this.hero.getEffects().stream()
                .filter(e->e.subTypes.contains(Effect.SubType.DEBUFF))
                .toList());
        if (debuff != null) {
            Effect debuffCopy = debuff.copy();
            this.hero.removeEffectByName(debuffCopy.getName());
            target.addEffect(debuffCopy, this.hero);
        }
        Effect buff = CollectionUtils.getRandom(target.getEffects().stream()
                .filter(e->e.subTypes.contains(Effect.SubType.BUFF))
                .toList());
        if (buff != null) {
            Effect buffCopy = buff.copy();
            target.removeEffectByName(buff.name);
            this.hero.addEffect(buffCopy, this.hero);
        }
    }
}
