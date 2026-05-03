package game.skills.trees.classes.flora;

import framework.Logger;
import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.TargetType;

public class S_LifeBlossom extends Skill {
    public void castChange(ConnectionPayload pl) {
        Logger.logLn("S_LifeBlossom.castChange()");
        if (pl.skill.equals(this) && this.hero.arena.globalEffect != null) {
            this.targetType = TargetType.ALL_TARGETS;
        }
    }
}
