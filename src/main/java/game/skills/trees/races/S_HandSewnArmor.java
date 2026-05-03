package game.skills.trees.races;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_HandSewnArmor extends Skill {
    public void chanceEffectMult(ConnectionPayload pl) {
        Logger.logLn("S_HandSewnArmor.chanceEffectMult()");
        pl.value = (int)keyValues.get("ChanceMult") * pl.value;
    }
    public void statBaseChangeChange(ConnectionPayload pl) {
        Logger.logLn("S_HandSewnArmor.statBaseChangeChange()");
        pl.value = (int) ((double) keyValues.get("StatMult") * pl.value);
    }
    public void statMultChangeChange(ConnectionPayload pl) {
        Logger.logLn("S_HandSewnArmor.statMultChangeChange()");
        pl.value = (int) ((double) keyValues.get("StatMult") * pl.value);
    }
}
