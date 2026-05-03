package game.skills.trees.classes.vengeance;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.libraries.EffectLibrary;
import game.effects.status.Immunity;
import game.skills.Skill;
import utils.Utils;

public class S_LookForTrouble extends Skill{
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        Logger.logLn("S_LookForTrouble.onMark()");
        this.marks++;
    }
    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_LookForTrouble.castChange()");
        if (pl.skill.equals(this)) {
            ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null);
            if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || marks > 4) {
                this.effects.add(EffectLibrary.getEffect(Immunity.class.getName(), 0,1,null));
            }
        }
    }
}
