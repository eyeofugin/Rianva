package game.skills.trees.roles.inspiration;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import utils.MyMaths;

public class S_SpellShield extends Skill {
    public void onPerform(ConnectionPayload pl) {
        int dmg = MyMaths.percentageOf((double)keyValues.get("NrgPercentage"), pl.skill.getManaCost());
        this.hero.shield(dmg, this.hero, this, null, null);
    }
}
