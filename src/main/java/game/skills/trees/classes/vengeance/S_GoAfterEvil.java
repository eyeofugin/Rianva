package game.skills.trees.classes.vengeance;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.DamageMode;
import game.skills.logic.DamageType;
import utils.Utils;

import java.util.List;

public class S_GoAfterEvil extends Skill {
    int marks = 0;
    public void onMark(ConnectionPayload pl) {
        this.marks++;
    }
    public void castChange(ConnectionPayload pl) {
        if (pl.skill.equals(this)) {
            ConnectionPayload.CondEffectImpact impact = Utils.condTriggerChanges(this.hero, this, null, null, pl.depth+1);
            if (impact.equals(ConnectionPayload.CondEffectImpact.ALLOW) || marks > 9) {
                this.staticDmg = (int) keyValues.get("StaticDmg");
                this.staticDmgTargets = (List<Integer>) keyValues.get("StaticDmgTargets");
                this.staticDamageMode = (DamageMode) keyValues.get("StaticDamageMode");
                this.staticDamageType = (DamageType) keyValues.get("StaticDamageType");
            }
        }
    }
}
