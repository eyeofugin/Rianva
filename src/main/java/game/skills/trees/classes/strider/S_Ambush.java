package game.skills.trees.classes.strider;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;

public class S_Ambush extends Skill {

    public void onTarget(ConnectionPayload pl) {
        Hero target = pl.skill.getTargets().getFirst();
        if (target == null) {
            return;
        }
        if (target.hasFieldEffect() || target.hasDebuff()) {
            pl.skill.push = 1;
        }
    }
}
