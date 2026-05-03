package game.skills.trees.equipment.sword;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.effects.hero.Advantage;
import game.skills.Skill;

public class S_DuelingSlash extends Skill {
    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_DuelingSlash.castChange()");
        if (this.hero.hasPermanentEffect(Advantage.class)) {
            this.move = 1;
        } else {
            this.move = -1;
        }
    }
}
