package game.skills.trees.roles.tempo;

import game.skills.Skill;
import game.skills.logic.Stat;
import utils.MyMaths;

public class S_Aid extends Skill {
    @Override
    public String getDescription() {
        if (this.hero == null) {
            return  "[CHN] 50% BODY chance to give {003}Mighty{001}." + " [BRL] [BRL] "
                    + "[CHN] 50% MIND chance to give {003}Enlightened{001}." + " [BRL] [BRL] "
                    + "[CHN] 50% DEXTERITY chance to give {003}Flawless{001}.";
        }
        String bodyChance = MyMaths.percentageOf(50, this.hero.getStat(Stat.BODY)) + "";
        String mindChance = MyMaths.percentageOf(50, this.hero.getStat(Stat.MIND)) + "";
        String dexChance = MyMaths.percentageOf(50, this.hero.getStat(Stat.DEXTERITY)) + "";

        return  "[CHN] "+bodyChance+"(50% BODY) chance to give {003}Mighty{001}." + " [BRL] [BRL] "
                + "[CHN] "+mindChance+"(50% MIND) chance to give {003}Enlightened{001}." + " [BRL] [BRL] "
                + "[CHN] "+dexChance+"(50% DEXTERITY) chance to give {003}Flawless{001}.";

    }
}
