package game.skills.trees.races;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Aggression extends Skill {
    public void chanceEffectMult(ConnectionPayload pl) {
        Logger.logLn("S_Aggression.chanceEffectMult()");
        pl.value = (int )keyValues.get("ChanceMult") * pl.value;
    }
}
