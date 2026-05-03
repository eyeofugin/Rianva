package game.skills.trees.roles.inspiration;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_MightAndMagic extends Skill {
    public void effectAdded(ConnectionPayload pl) {
        Logger.logLn("S_MightAndMagic.effectAdded()");
        this.hero.percentageEnergy((int)keyValues.get("NrgPercentage"), this.hero, this, null, null, false);
    }
}
