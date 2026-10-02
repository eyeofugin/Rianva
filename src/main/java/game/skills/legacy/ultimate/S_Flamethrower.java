package game.skills.legacy.ultimate;

import framework.connector.ConnectionPayload;
import game.skills.Skill;
import game.skills.legacy.logic.TargetType;

public class S_Flamethrower extends Skill {

    public void castChange(ConnectionPayload pl) {
        this.targetType = TargetType.ALL_TARGETS;
    }
}