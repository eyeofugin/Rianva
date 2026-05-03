package game.skills.trees.roles.resolve;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_IronClad extends Skill {
    public void statBaseChangeChange(ConnectionPayload pl) {
        Logger.logLn("S_IronClad.statBaseChangeChange()");
        pl.value = (int) ((double) keyValues.get("StatMult") * pl.value);
    }
    public void statMultChangeChange(ConnectionPayload pl) {
        Logger.logLn("S_IronClad.statMultChangeChange()");
        pl.value = (int) ((double) keyValues.get("StatMult") * pl.value);
    }
}
