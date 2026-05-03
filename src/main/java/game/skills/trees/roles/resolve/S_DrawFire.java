package game.skills.trees.roles.resolve;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_DrawFire extends Skill {
    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_DrawFire.castChange()");
        pl.skill.getEffects().stream().filter(e->e.turns>0).forEach(e->e.turns+=2);
    }
}
