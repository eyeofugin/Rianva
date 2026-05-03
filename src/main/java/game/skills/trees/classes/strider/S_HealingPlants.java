package game.skills.trees.classes.strider;

import framework.Logger;
import game.effects.status.Bleeding;
import game.effects.status.Blinded;
import game.effects.status.Poisoned;
import game.entities.Hero;
import game.skills.Skill;

public class S_HealingPlants extends Skill {

    @Override
    public void customTargetEffect(Hero target){
        Logger.logLn("S_HealingPlants.customTargetEffect()");
        target.removeEffectByName(Bleeding.class.getName());
        target.removeEffectByName(Poisoned.class.getName());
        target.removeEffectByName(Blinded.class.getName());
    }
}
