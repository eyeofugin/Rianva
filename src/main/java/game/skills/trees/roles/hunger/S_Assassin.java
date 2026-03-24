package game.skills.trees.roles.hunger;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import utils.MyMaths;

public class S_Assassin extends Skill {
    public void dmgChanges(ConnectionPayload pl) {
        if (pl.target.getCurrentLifePercentage() < (int) keyValues.get("LifeCutOff")) {
            int change = MyMaths.percentageOf((double)keyValues.get("DmgPercentage"), pl.dmg);
            pl.dmg += change;
        }
    }
}
