package game.skills.trees.equipment.sword;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_ShatterSwing extends Skill {
    public void chanceChange(ConnectionPayload pl) {
        pl.value *= 2;
    }
}
