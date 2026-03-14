package game.skills.trees.races;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_BornInFlames extends Skill {
    public void effectFailure(ConnectionPayload pl) {
        pl.failure = true;
    }
}
