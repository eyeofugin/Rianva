package game.skills.trees.roles.resolve;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_IronClad extends Skill {
    public void statBaseChangeChange(ConnectionPayload pl) {
        pl.value = (int) ((double) keyValues.get("StatMult") * pl.value);
    }
    public void statMultChangeChange(ConnectionPayload pl) {
        pl.value = (int) ((double) keyValues.get("StatMult") * pl.value);
    }
}
