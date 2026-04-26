package game.skills.trees.equipment.bow;

import game.skills.Skill;
import game.skills.logic.Stat;
import utils.MyMaths;

public class S_CripplingShot extends Skill {
    @Override
    public String getDescription() {
        if (this.hero == null) {
            return  "Give Immobile 3. [BRL] "
                    + "[CHN] 75% BODY chance to give {004}Bleeding{001} 2.";
        }
        String bodyChance = MyMaths.percentageOf(75, this.hero.getStat(Stat.BODY)) + "";

    return "Give Immobile 3. [BRL] "
        + "[CHN] "+bodyChance+"(75% BODY) chance to give {004}Bleeding{001} 2.";
    }
}
