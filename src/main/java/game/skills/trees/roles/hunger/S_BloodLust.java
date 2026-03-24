package game.skills.trees.roles.hunger;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.hero.Marked;
import game.skills.Skill;
import utils.MyMaths;

public class S_BloodLust extends Skill {
    private int marks = 0;
    public void dmgTrigger(ConnectionPayload pl) {
        pl.target.addEffect(EffectLibrary.getEffect(Marked.class.getName(), 0, 0, null), this.hero);
    }
    public void onMark(ConnectionPayload pl) {
        this.marks++;
    }
    public void dmgChangesMult(ConnectionPayload pl) {
        int change = MyMaths.percentageOf((double)keyValues.get("PercentagePerStack") * marks, pl.dmg);
        pl.dmg += change;
    }
}
