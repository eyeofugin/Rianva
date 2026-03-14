package game.skills.trees.equipment.sword;

import framework.connector.ConnectionPayload;
import game.effects.hero.Advantage;
import game.skills.Skill;

public class S_DuelingSlash extends Skill {
    public void castChange(ConnectionPayload pl) {
        if (this.hero.hasPermanentEffect(Advantage.class)) {
            this.move = 1;
        } else {
            this.move = -1;
        }
    }
}
