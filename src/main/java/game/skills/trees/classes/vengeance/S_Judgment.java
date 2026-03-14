package game.skills.trees.classes.vengeance;

import framework.connector.ConnectionPayload;
import game.effects.EffectLibrary;
import game.effects.hero.Marked;
import game.effects.status.Debuff;
import game.entities.Hero;
import game.skills.Skill;
import utils.Utils;

public class S_Judgment extends Skill {
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        this.marks++;
    }
    public void castChange(ConnectionPayload pl) {
        if (pl.skill.equals(this)) {
            ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null, pl.depth+1);
            if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || marks > 9) {
                this.effects.add(EffectLibrary.getEffect(Debuff.class.getName(), 0, 0, null));
            }
        }
    }
}
