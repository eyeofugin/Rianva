package game.skills.trees.roles.tempo;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Logistics extends Skill {
    private int spellNr = 1;
    public void onPerform(ConnectionPayload pl) {
        Logger.logLn("S_Logistics.onPerform()");
        spellNr++;
        if (spellNr > 3) {
            spellNr = 1;
        }
    }

    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_Logistics.castChange()");
        if (spellNr == 3) {
            pl.skill.setManaCost(0);
        }
    }
}
