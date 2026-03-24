package game.skills.trees.roles.inspiration;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;
import utils.MyMaths;

public class S_TimeTrick extends Skill {

    @Override
    public void customTargetEffect(Hero target) {
        target.changeRandomActiveCdBy(-1);
    }
}
