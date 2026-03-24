package game.skills.trees.roles.inspiration;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_MightAndMagic extends Skill {
    public void effectAdded(ConnectionPayload pl) {
        this.hero.percentageEnergy((int)keyValues.get("NrgPercentage"), this.hero, this, null, null, false);
    }
}
