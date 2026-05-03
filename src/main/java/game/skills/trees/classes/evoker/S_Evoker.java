package game.skills.trees.classes.evoker;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import utils.MyMaths;

public class S_Evoker extends Skill {


    public void onPerform(ConnectionPayload pl) {
        Logger.logLn("S_Evoker.onPerform()");
        int dmg = MyMaths.percentageOf((int)keyValues.get("NrgPercentage"), pl.skill.getManaCost());
        this.hero.damage(dmg, DamageType.TRUE, DamageMode.EFFECT, this.hero, this, null, null, 0);
    }
    public void dmgChanges(ConnectionPayload pl) {
        Logger.logLn("S_Evoker.dmgChanges()");
        if (!this.equals(pl.skill)) {
            int change = MyMaths.percentageOf((int) keyValues.get("DmgPercentage"), pl.dmg);
            pl.dmg += change;
        }
    }
}