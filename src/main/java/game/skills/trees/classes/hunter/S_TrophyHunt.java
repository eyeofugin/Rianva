package game.skills.trees.classes.hunter;

import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.hero.Trophy;
import game.skills.Skill;
import game.skills.logic.Stat;
import utils.MyMaths;
import utils.Utils;

public class S_TrophyHunt extends Skill {
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        this.marks++;
        this.hero.addEffect(EffectLibrary.getEffect(Trophy.class.getName(), 1, 0, null), this.hero);
    }
    public void startOfRound(ConnectionPayload pl) {
        if (marks >= 20) {
            this.hero.arena.extraTurn(this.hero);
        }
    }
    public void statChange(ConnectionPayload pl) {
        if (marks > 9) {
            int change = MyMaths.percentageOf((int) keyValues.get("DextIncrease"), pl.value);
            change = Utils.statChangesChanges(this.hero, this.hero, Stat.DEXTERITY, change, this, null, null);
            pl.value += change;
        }
    }

    public void dmgChanges(ConnectionPayload pl) {
        if (marks > 4) {
            int change = MyMaths.percentageOf((int)keyValues.get("DmgIncrease"), pl.dmg);
            pl.dmg += change;
        }
    }
}
