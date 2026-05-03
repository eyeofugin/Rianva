package game.skills.trees.classes.rogue;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.MyMaths;
import utils.Utils;

public class S_Rogue extends Skill {
    public void statChange(ConnectionPayload pl) {
        Logger.logLn("S_Rogue.statChange()");
        int change = MyMaths.percentageOf((double)keyValues.get("Percentage"), this.hero.getStat(Stat.DEXTERITY));
        change = Utils.statChangesChanges(this.hero, this.hero, Stat.DEXTERITY, change, this, null, null);
        pl.value += change;
    }
}
