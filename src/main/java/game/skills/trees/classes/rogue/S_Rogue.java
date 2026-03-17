package game.skills.trees.classes.rogue;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.MyMaths;
import utils.Utils;

public class S_Rogue extends Skill {
    public void statChange(ConnectionPayload pl) {
        int change = MyMaths.percentageOf((double)keyValues.get("Percentage"), this.hero.getStat(Stat.DEXTERITY));
        change = Utils.statChangesChanges(this.hero, this.hero, Stat.DEXTERITY, change, this, null, null, pl.depth + 1);
        pl.value += change;
    }
}
