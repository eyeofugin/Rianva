package game.skills.trees.roles.inspiration;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import utils.MyMaths;

public class S_ManaFlow extends Skill {
    public void endOfTurn(ConnectionPayload pl) {
        int nrg = MyMaths.percentageOf((double)keyValues.get("NrgPercentage"), pl.skill.getManaCost());
        this.hero.percentageEnergy(nrg, this.hero, this, null, null, false);
    }
}
