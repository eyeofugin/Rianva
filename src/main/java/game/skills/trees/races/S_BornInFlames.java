package game.skills.trees.races;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_BornInFlames extends Skill {
    public void effectFailure(ConnectionPayload pl) {
        Logger.logLn("S_BornInFlames.effectFailure()");
        pl.failure = true;
    }
}
