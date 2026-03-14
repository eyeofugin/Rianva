package game.skills.trees.races;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_KeenEye extends Skill {
    public void statChange(ConnectionPayload pl) {
        if (this.hero.equals(pl.target)) {
            pl.value += (int) keyValues.get("SelfAcc");
        } else {
            pl.value += (int) keyValues.get("OtherAcc");
        }
    }
}
