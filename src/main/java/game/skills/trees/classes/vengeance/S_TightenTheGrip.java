package game.skills.trees.classes.vengeance;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;
import utils.Utils;

public class S_TightenTheGrip extends Skill {
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        Logger.logLn("S_TightenTheGrip.onMark()");
        this.marks++;
    }
    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_TightenTheGrip.castChange()");
        if (pl.skill.equals(this)) {
            ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
            if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || marks > 9) {
                this.effects.getFirst().turns++;
            }
        }
    }
}
