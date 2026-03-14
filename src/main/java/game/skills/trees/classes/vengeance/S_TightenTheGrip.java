package game.skills.trees.classes.vengeance;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import utils.Utils;

public class S_TightenTheGrip extends Skill {
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        this.marks++;
    }
    public void castChange(ConnectionPayload pl) {
        if (pl.skill.equals(this)) {
            ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null, pl.depth+1);
            if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || marks > 9) {
                this.effects.getFirst().turns++;
            }
        }
    }
}
