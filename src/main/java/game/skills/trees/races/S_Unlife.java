package game.skills.trees.races;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Unlife extends Skill {
    public void healChanges(ConnectionPayload pl) {
        pl.heal = 0;
    }
}
