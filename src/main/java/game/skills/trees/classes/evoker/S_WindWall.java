package game.skills.trees.classes.evoker;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.MyMaths;

public class S_WindWall extends Skill {

    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_WindWall.castChange()");
        if (this.equals(pl.skill)) {
            int chance = MyMaths.percentageOf((int)keyValues.get("ChancePercentage"), this.hero.getStat(Stat.VITALITY));
            if (MyMaths.success(chance)) {
                pl.skill.push = 2;
            }
        }
    }
}