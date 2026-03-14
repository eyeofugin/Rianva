package game.skills.trees.races;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class S_Agility extends Skill {
    private boolean reduced = false;
    public void castChange(ConnectionPayload pl) {
        if (reduced) {
            pl.skill.setMaxCd(pl.skill.getMaxCd() - 1);
            reduced = false;
        } else {
            reduced = true;
        }
    }
}
