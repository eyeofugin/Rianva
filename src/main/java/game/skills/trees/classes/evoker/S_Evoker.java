package game.skills.trees.classes.evoker;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import utils.MyMaths;

public class S_Evoker extends Skill {


    public void onPerform(ConnectionPayload pl) {
        int dmg = MyMaths.percentageOf((double)keyValues.get("NrgPercentage"), pl.skill.getManaCost());
        this.hero.damage(dmg, DamageType.TRUE, DamageMode.EFFECT, this.hero, this, null, null, 0);
    }
    public void dmgChanges(ConnectionPayload pl) {
        int change = MyMaths.percentageOf((double) keyValues.get("DmgPercentage"), pl.dmg);
        pl.value += change;
    }
}