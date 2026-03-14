package game.skills.trees.races;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_HandSewnArmor extends Skill {
    public void chanceEffectMult(ConnectionPayload pl) {
        pl.value = (int)keyValues.get("ChanceMult") * pl.value;
    }
    public void statBaseChangeChange(ConnectionPayload pl) {
        pl.value = (int) ((double) keyValues.get("StatMult") * pl.value);
    }
}
