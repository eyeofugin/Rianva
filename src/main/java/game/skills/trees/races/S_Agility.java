package game.skills.trees.races;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Agility extends Skill {
    private boolean reduced = true;
    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_Agility.castChange()");
        if (reduced) {
            pl.skill.setMaxCd(Math.max(0,pl.skill.getMaxCd() - 1));
        }
    }
    public void sot(ConnectionPayload pl) {
        Logger.logLn("S_Agility.sot()");
        reduced = !reduced;
    }
}
