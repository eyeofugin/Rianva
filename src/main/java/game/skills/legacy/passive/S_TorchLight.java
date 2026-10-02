package game.skills.legacy.passive;

import framework.connector.ConnectionPayload;
import game.effects.status.Burning;
import game.skills.Skill;

public class S_TorchLight extends Skill {

    public void onTarget(ConnectionPayload pl) {
        if (pl.target.hasPermanentEffect(Burning.class)) {
            this.cannotMiss = true;
        }
    }
}
