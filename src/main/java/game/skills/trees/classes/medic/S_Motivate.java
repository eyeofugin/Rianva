package game.skills.trees.classes.medic;

import game.entities.Hero;
import game.skills.Skill;

public class S_Motivate extends Skill {
    @Override
    public void individualResolve(Hero target) {
        target.revertStatEffects(this.hero);
    }
}
