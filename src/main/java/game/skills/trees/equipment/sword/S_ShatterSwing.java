package game.skills.trees.equipment.sword;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_ShatterSwing extends Skill {
    public void chanceChange(ConnectionPayload pl) {
        Logger.logLn("S_ShatterSwing.chanceChange()");
        pl.value *= 2;
    }
}
