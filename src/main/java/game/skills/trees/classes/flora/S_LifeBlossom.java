package game.skills.trees.classes.flora;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.logic.TargetType;

public class S_LifeBlossom extends Skill {
    public void castChange(ConnectionPayload pl) {
        if (pl.skill.equals(this) && this.hero.arena.globalEffect != null) {
            this.targetType = TargetType.ALL_TARGETS;
        }
    }
}
