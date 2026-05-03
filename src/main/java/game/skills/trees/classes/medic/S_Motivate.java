package game.skills.trees.classes.medic;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;

public class S_Motivate extends Skill {
    @Override
    public void individualResolve(Hero target) {
        Logger.logLn("S_Motivate.individualResolve()");
        target.revertStatEffects(this.hero);
    }
}
