package game.skills.trees.races;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Unlife extends Skill {
    public void healChanges(ConnectionPayload pl) {
        Logger.logLn("S_Unlife.healChanges()");
        pl.heal = 0;
    }
}
